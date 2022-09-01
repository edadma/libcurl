package io.github.edadma.libcurl

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*
import scala.scalanative.libc.*
import io.github.edadma.libcurl.extern.LibCurl.*

import scala.collection.mutable
import stdlib.*
import stdio.*
import string.*
import scala.collection.mutable.HashMap

@main def run(): Unit =
//  globalInit(GLOBAL_ALL)
//
//  val curl = easyInit
//
//  if curl.nonNull then
//    curl.easySetopt(CurlOption.URL, "http://localhost:3000")
//    curl.easySetopt(CurlOption.VERBOSE, 1L)
//    curl.easySetoptWriteFunction(a => println(new String(a)))
//    curl.easyPerform match
//      case Code.OK =>
//        println(curl.easyGetinfo(Info.RESPONSE_CODE))
//        curl.easyCleanup()
//      case c => println(easyStrerror(c))
//
//  globalCleanup()

  globalInit(GLOBAL_ALL)

  val curl = easyInit

  if curl.nonNull then
    curl.easySetopt(CurlOption.URL, "http://localhost:3000")
    curl.easySetopt(CurlOption.VERBOSE, 1L)
    curl.easySetoptWriteFunction(a => println(new String(a)))
    curl.easyPerform match
      case Code.OK =>
        println(curl.easyGetinfo(Info.RESPONSE_CODE))
        curl.easyCleanup()
      case c => println(easyStrerror(c))

  globalCleanup()

  val dataCB =
    (ptr: Ptr[Byte], size: CSize, nmemb: CSize, data: Ptr[Byte]) => {
      val serial = !(data.asInstanceOf[Ptr[Long]])
      val len = stackalloc[Double]()
      !len = 0
      val strData = bufferToString(ptr, size, nmemb)
      println(s"req $serial: got data of size $size x $nmemb")

      val resp = requests(serial)
      resp.body = resp.body + strData
      requests(serial) = resp

      return size * nmemb
    }

  val headerCB =
    (ptr: Ptr[Byte], size: CSize, nmemb: CSize, data: Ptr[Byte]) => {
      val serial = !(data.asInstanceOf[Ptr[Long]])
      val len = stackalloc[Double]()
      !len = 0
      val strData = bufferToString(ptr, size, nmemb)
      println(s"req $serial: got header line of size $size x $nmemb")

      val resp = requests(serial)
      resp.body = resp.body + strData
      requests(serial) = resp

      return size * nmemb
    }

  val socketCB =
    (curl: Curl, socket: Ptr[Byte], action: Int, data: Ptr[Byte], socket_data: Ptr[Byte]) => {
      println(s"socketCB called with action $action")
      val pollHandle = if (socket_data == null) {
        println(s"initializing handle for socket $socket")
        val buf = malloc(uv_handle_size(UV_POLL_T)).asInstanceOf[Ptr[Ptr[Byte]]]
        !buf = socket
        check(uv_poll_init_socket(loop, buf, socket), "uv_poll_init_socket")
        check(multi_assign(multi, socket, buf.asInstanceOf[Ptr[Byte]]), "multi_assign")
        buf
      } else {
        socket_data.asInstanceOf[Ptr[Ptr[Byte]]]
      }

      val events = action match {
        case POLL_NONE   => None
        case POLL_IN     => Some(UV_READABLE)
        case POLL_OUT    => Some(UV_WRITABLE)
        case POLL_INOUT  => Some(UV_READABLE | UV_WRITABLE)
        case POLL_REMOVE => None
      }

      events match {
        case Some(ev) =>
          println(s"starting poll with events $ev")
          uv_poll_start(pollHandle, ev, pollCB)
        case None =>
          println("stopping poll")
          uv_poll_stop(pollHandle)
          startTimerCB(multi, 1, null)
      }
      0
    }

  val pollCB =
    (pollHandle: PollHandle, status: Int, events: Int) => {
      println(s"""ready_for_curl fired with status $status and
                events $events""")
      val socket = !(pollHandle.asInstanceOf[Ptr[Ptr[Byte]]])
      val actions = (events & 1) | (events & 2)
      val running_handles = stackalloc[Int]()
      val result = multi_socket_action(multi, socket, actions, running_handles)
      println("multi_socket_action", result)
    }

  val startTimerCB =
    (curl: MultiCurl, timeout_ms: Long, data: Ptr[Byte]) => {
      println(s"start_timer called with timeout $timeout_ms ms")
      val time = if (timeout_ms < 1) {
        println("setting effective timeout to 1")
        1
      } else timeout_ms
      println("starting timer")
      check(uv_timer_start(timerHandle, timeoutCB, time, 0), "uv_timer_start")
      cleanup_requests()
      0
    }

  val timeoutCB =
    (handle: TimerHandle) => {
      println("in timeout callback")
      val running_handles = stackalloc[Int]()
      multi_socket_action(multi, int_to_ptr(-1), 0, running_handles)
      println(s"on_timer fired, ${!running_handles} sockets running")
    }

//  var request_serial = 0L
//  val responses = mutable.HashMap[Long, ResponseState]()
//
//  val statusLine = raw".+? (\d+) (.+)\n".r
//  val headerLine = raw"([^:]+): (.*)\n".r
//
//  def func_to_ptr(f: Object): Ptr[Byte] = {
//    Boxes.boxToPtr[Byte](Boxes.unboxToCFuncPtr4(f))
//  }
//
//  def int_to_ptr(i: Int): Ptr[Byte] = {
//    Boxes.boxToPtr[Byte](Intrinsics.castIntToRawPtr(i))
//  }
//
//  def long_to_ptr(l: Long): Ptr[Byte] = {
//    Boxes.boxToPtr[Byte](Intrinsics.castLongToRawPtr(l))
//  }
//
//  def bufferToString(ptr: Ptr[Byte], size: CSize, nmemb: CSize): String = {
//    val byteSize = size * nmemb
//    val buffer = malloc(byteSize + 1.toUInt)
//    strncpy(buffer, ptr, byteSize + 1.toUInt)
//    val res = fromCString(buffer)
//    free(buffer)
//    res
//  }
//
//  val writeCB: CurlDataCallback =
//    (ptr: Ptr[Byte], size: CSize, nmemb: CSize, data: Ptr[Byte]) => {
//      val serial = !(data.asInstanceOf[Ptr[Long]])
//      val len = stackalloc[Double]()
//      !len = 0
//      val strData = bufferToString(ptr, size, nmemb)
//
//      val resp = responses(serial)
//      resp.body = resp.body + strData
//      responses(serial) = resp
//
//      return size * nmemb
//    }
//
//  val headerCB: CurlDataCallback =
//    (ptr: Ptr[Byte], size: CSize, nmemb: CSize, data: Ptr[Byte]) => {
//      val serial = !(data.asInstanceOf[Ptr[Long]])
//      val len = stackalloc[Double]()
//      !len = 0
//      val byteSize = size * nmemb
//      val headerString = bufferToString(ptr, size, nmemb)
//      headerString match {
//        case statusLine(code, description) =>
//          println(s"status code: $code $description")
//        case headerLine(k, v) =>
//          val resp = responses(serial)
//          resp.headers(k) = v
//          responses(serial) = resp
//        case l =>
//      }
//      fwrite(ptr, size, nmemb, stdout)
//      byteSize
//    }
//
//  def addHeaders(curl: Curl, headers: Seq[String]): Ptr[CurlSList] = {
//    var slist: Ptr[CurlSList] = null
//    for (h <- headers) {
//      addHeader(slist, h)
//    }
//    curl_easy_setopt(curl, HTTPHEADER, slist.asInstanceOf[Ptr[Byte]])
//    slist
//  }
//
//  def addHeader(slist: Ptr[CurlSList], header: String): Ptr[CurlSList] = Zone { implicit z =>
//    slist_append(slist, toCString(header))
//  }
//
//  def getSync(url: String, headers: Seq[String] = Seq.empty): ResponseState = {
//    val req_id_ptr = malloc(sizeof[Long]).asInstanceOf[Ptr[Long]]
//    !req_id_ptr = 1 + request_serial
//    request_serial += 1
//    responses(request_serial) = ResponseState()
//    val curl = easy_init()
//
//    Zone { implicit z =>
//      val url_str = toCString(url)
//      println(curl_easy_setopt(curl, URL, url_str))
//    }
//    curl_easy_setopt(curl, WRITECALLBACK, func_to_ptr(writeCB))
//    curl_easy_setopt(curl, WRITEDATA, req_id_ptr.asInstanceOf[Ptr[Byte]])
//    curl_easy_setopt(curl, HEADERCALLBACK, func_to_ptr(headerCB))
//    curl_easy_setopt(curl, HEADERDATA, req_id_ptr.asInstanceOf[Ptr[Byte]])
//    val res = easy_perform(curl)
//    easy_cleanup(curl)
//    responses(request_serial)
//  }
//
//  println("initializing")
//  global_init(1)
//  val resp = getSync(url)
//  println(s"done.  got response: $resp")
//  println("global cleanup...")
//  global_cleanup()
//  println("done")
