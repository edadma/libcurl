package io.github.edadma.libcurl.extern

import scala.scalanative.unsafe._

@link("curl")
@extern object LibCurl:
  type CURL = Ptr[Byte]
  type CURLoption = CInt
  type CURLINFO = CInt
  type CURLcode = CInt

  type CurlRequest = CStruct4[Ptr[Byte], Long, Long, Int]
  type CURLMsg = CStruct3[Int, CURL, Ptr[Byte]]
  type CURLMsgp = Ptr[CStruct3[Int, CURL, Ptr[Byte]]]

  type CurlDataCallback = CFuncPtr4[Ptr[Byte], CSize, CSize, Ptr[Byte], CSize]
  type CurlSocketCallback = CFuncPtr5[CURL, Ptr[Byte], CInt, Ptr[Byte], Ptr[Byte], CInt]
  type CurlTimerCallback = CFuncPtr3[CURLM, Long, Ptr[Byte], CInt]

  type CURLM = Ptr[Byte]

  type CurlSList = CStruct2[Ptr[Byte], CString]

  def curl_global_init(flags: CLong): CURLcode = extern

  def curl_easy_init: CURL = extern

  def curl_easy_setopt(curl: CURL, option: CURLoption, args: CVarArg*): CURLcode = extern

  def curl_easy_getinfo(handle: CURL, info: CInt, args: Ptr[Byte]): CInt = extern

  def curl_easy_perform(easy_handle: CURL): CInt = extern

  def curl_slist_append(slist: Ptr[CurlSList], string: CString): Ptr[CurlSList] = extern

  def curl_slist_free_all(slist: Ptr[CurlSList]): Unit = extern

  def curl_global_cleanup(): Unit = extern

  def curl_easy_cleanup(handle: CURL): Unit = extern

  def curl_multi_init(): CURLM = extern

  def curl_multi_add_handle(multi: CURLM, easy: CURL): Int = extern

  def curl_multi_setopt(multi: CURLM, option: CInt, parameter: CVarArg): CInt = extern

  def curl_multi_setopt_ptr(multi: CURLM, option: CInt, parameter: Ptr[Byte]): CInt = extern

  def curl_multi_assign(multi: CURLM, socket: Ptr[Byte], socket_data: Ptr[Byte]): Int = extern

  def curl_multi_socket_action(multi: CURLM, socket: Ptr[Byte], events: Int, numhandles: Ptr[Int]): Int = extern

  def curl_multi_info_read(multi: CURLM, message: Ptr[CInt]): CURLMsg = extern

  def curl_multi_perform(multi: CURLM, numhandles: Ptr[Int]): Int = extern

  def curl_multi_cleanup(multi: CURLM): Int = extern

  def curl_easy_strerror(code: Int): CString = extern
