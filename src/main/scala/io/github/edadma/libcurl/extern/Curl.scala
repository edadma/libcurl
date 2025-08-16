package io.github.edadma.libcurl.extern

import scala.scalanative.unsafe._

type CURL          = Ptr[Byte]
type CURLcode      = CInt
type WriteCallback = CFuncPtr4[Ptr[Byte], CSize, CSize, Ptr[Byte], CSize]

@link("curl")
@extern
object Curl:
  def curl_easy_init(): CURL                  = extern
  def curl_easy_cleanup(curl: CURL): Unit     = extern
  def curl_easy_perform(curl: CURL): CURLcode = extern

  // Multiple overloads for different parameter types
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: CString): CURLcode       = extern
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: WriteCallback): CURLcode = extern
  def curl_easy_setopt(curl: CURL, option: CInt, parameter: CLong): CURLcode         = extern
