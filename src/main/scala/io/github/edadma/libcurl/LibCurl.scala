package io.github.edadma.libcurl

import scala.scalanative.unsafe._

@link("curl")
@extern object LibCurl:
  type CurlBuffer = CStruct2[CString, CSize]

  type Curl = Ptr[Byte]
  type CurlOption = Int
  type CurlInfo = CInt

  def curl_global_init(flags: CLong): Unit = extern

  def curl_easy_init(): Curl = extern

  def curl_easy_setopt(handle: Curl, option: CInt, parameter: Ptr[Byte]): CInt = extern

  def curl_easy_getinfo(handle: Curl, info: CInt, parameter: Ptr[Byte]): CInt = extern

  def curl_easy_perform(easy_handle: Curl): CInt = extern

  type CurlSList = CStruct2[Ptr[Byte], CString]

  def curl_slist_append(slist: Ptr[CurlSList], string: CString): Ptr[CurlSList] = extern

  def curl_slist_free_all(slist: Ptr[CurlSList]): Unit = extern

  def curl_global_cleanup(): Unit = extern

  def curl_easy_cleanup(handle: Curl): Unit = extern

  type CurlRequest = CStruct4[Ptr[Byte], Long, Long, Int]
  type CurlMessage = CStruct3[Int, Curl, Ptr[Byte]]

  type CurlDataCallback = CFuncPtr4[Ptr[Byte], CSize, CSize, Ptr[Byte], CSize]
  type CurlSocketCallback = CFuncPtr5[Curl, Ptr[Byte], CInt, Ptr[Byte], Ptr[Byte], CInt]
  type CurlTimerCallback = CFuncPtr3[MultiCurl, Long, Ptr[Byte], CInt]

  type MultiCurl = Ptr[Byte]

  def curl_multi_init(): MultiCurl = extern

  def curl_multi_add_handle(multi: MultiCurl, easy: Curl): Int = extern

  def curl_multi_setopt(multi: MultiCurl, option: CInt, parameter: CVarArg): CInt = extern

  def curl_multi_setopt_ptr(multi: MultiCurl, option: CInt, parameter: Ptr[Byte]): CInt = extern

  def curl_multi_assign(multi: MultiCurl, socket: Ptr[Byte], socket_data: Ptr[Byte]): Int = extern

  def curl_multi_socket_action(multi: MultiCurl, socket: Ptr[Byte], events: Int, numhandles: Ptr[Int]): Int = extern

  def curl_multi_info_read(multi: MultiCurl, message: Ptr[Int]): Ptr[CurlMessage] = extern

  def curl_multi_perform(multi: MultiCurl, numhandles: Ptr[Int]): Int = extern

  def curl_multi_cleanup(multi: MultiCurl): Int = extern

  def curl_easy_strerror(code: Int): CString = extern
