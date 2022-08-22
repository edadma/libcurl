package io.github.edadma.libcurl

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*
import scala.scalanative.libc.*
import io.github.edadma.libcurl.extern.LibCurl.*
import LibCurlConstants.*

import scala.collection.mutable
import stdlib.*
import stdio.*
import string.*
import scala.collection.mutable.HashMap

@main def run(): Unit =
  globalInit(GLOBAL_ALL)

  val curl = easyInit

  if curl.nonNull then
    curl.easySetopt(CurlOption.URL, "http://localhost:3000")
    curl.easySetoptWriteFunction(a => println(new String(a)))
    curl.easyPerform match
      case Code.OK =>
        println(curl.easyGetinfo(Info.RESPONSE_CODE))
        curl.easyCleanup()
      case c => println(easyStrerror(c))

  globalCleanup()

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
