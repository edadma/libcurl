package io.github.edadma.libcurl.extern

import scala.scalanative.unsafe._

type CURL          = Ptr[Byte]
type CURLcode      = CInt
type WriteCallback = CFuncPtr4[Ptr[Byte], CSize, CSize, Ptr[Byte], CSize]

@link("curl")
@extern
object LibCurl:
  def curl_easy_init(): CURL                                                     = extern
  def curl_easy_cleanup(curl: CURL): Unit                                        = extern
  def curl_easy_perform(curl: CURL): CURLcode                                    = extern
  def curl_easy_getinfo(curl: CURL, info: CInt, parameter: Ptr[CLong]): CURLcode = extern

  // Multiple overloads - avoid CString + Ptr[Byte] conflict
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: CString): CURLcode       = extern
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: WriteCallback): CURLcode = extern
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: CLong): CURLcode         = extern
