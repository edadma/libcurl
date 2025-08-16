package io.github.edadma.libcurl.extern

import scala.scalanative.unsafe._

type CURL          = Ptr[Byte]
type CURLcode      = CInt
type WriteCallback = CFuncPtr4[Ptr[Byte], CSize, CSize, Ptr[Byte], CSize]
type curl_slist    = Ptr[Byte] // Opaque pointer to header list

@link("curl")
@extern
object LibCurl:
  def curl_easy_init(): CURL                                                     = extern
  def curl_easy_cleanup(curl: CURL): Unit                                        = extern
  def curl_easy_perform(curl: CURL): CURLcode                                    = extern
  def curl_easy_getinfo(curl: CURL, info: CInt, parameter: Ptr[CLong]): CURLcode = extern

  // Multiple overloads - avoid type erasure conflicts
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: Ptr[Byte]): CURLcode     = extern // For CString, curl_slist
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: WriteCallback): CURLcode = extern
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: CLong): CURLcode         = extern

  // Header list management
  def curl_slist_append(list: curl_slist, string: CString): curl_slist = extern
  def curl_slist_free_all(list: curl_slist): Unit                      = extern
