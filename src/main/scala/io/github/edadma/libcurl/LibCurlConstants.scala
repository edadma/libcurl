package io.github.edadma.libcurl

import scala.scalanative.unsafe._

object LibCurlConstants {
  import io.github.edadma.libcurl.extern.LibCurl._

//  val URL: CurlOption = 10002
//  val PORT: CurlOption = 10003
//  val USERPASSWORD: CurlOption = 10005
//
//  val READDATA: CurlOption = 10009
//  val HEADERDATA: CurlOption = 10029
//  val WRITEDATA: CurlOption = 10001
//
//  val READCALLBACK: CurlOption = 20012
//  val HEADERCALLBACK: CurlOption = 20079
//  val WRITECALLBACK: CurlOption = 20011
//
//  val TIMEOUT: CurlOption = 13
//  val GET: CurlOption = 80
//  val POST: CurlOption = 47
//  val PUT: CurlOption = 54
//  val CONTENTLENGTHDOWNLOADT: CurlInfo = 0x300000 + 15
//  val HTTPHEADER: CurlOption = 10023
//
//  val PRIVATEDATA: CurlOption = 10103
//  val GET_PRIVATEDATA: CurlInfo = 0x100000 + 21

  val SOCKETFUNCTION: CurlOption = 20001
  type SocketCallback = CFuncPtr5[Curl, Ptr[Byte], CInt, Ptr[Byte], Ptr[Byte], CInt]
  val TIMERFUNCTION: CurlOption = 20004
  type TimerCallback = CFuncPtr3[CURLM, Long, Ptr[Byte], CInt]

  type CurlAction = CInt
  val POLL_NONE: CurlAction = 0
  val POLL_IN: CurlAction = 1
  val POLL_OUT: CurlAction = 2
  val POLL_INOUT: CurlAction = 3
  val POLL_REMOVE: CurlAction = 4
}
