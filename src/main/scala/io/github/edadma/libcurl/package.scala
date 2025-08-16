package io.github.edadma.libcurl

import io.github.edadma.libcurl.extern.{LibCurl, CURLcode, WriteCallback}
import scala.scalanative.unsafe._
import scala.collection.mutable.ArrayBuffer
import scala.collection.concurrent.TrieMap

// Response class with binary support
case class HttpResponse(body: Array[Byte], statusCode: Int, success: Boolean):
  def bodyAsString: String                  = new String(body, "UTF-8")
  def bodyAsString(charset: String): String = new String(body, charset)

class CurlException(message: String) extends RuntimeException(message)

// Curl option constants
val CURLOPT_URL: CInt           = 10002
val CURLOPT_WRITEFUNCTION: CInt = 20011
val CURLOPT_WRITEDATA: CInt     = 10001

// Curl info constants
val CURLINFO_RESPONSE_CODE: CInt = 2097154

// Curl error codes
val CURLE_OK: CURLcode = 0

// Per-request response buffers using simple counter (thread-safe)
private val requestBuffers = TrieMap[Long, ArrayBuffer[Byte]]()

// Write callback that handles binary data
private val writeCallback: WriteCallback =
  CFuncPtr4.fromScalaFunction { (contents, size, nmemb, userdata) =>
    val realsize    = size * nmemb
    val realsizeInt = realsize.toInt

    if userdata != null then
      // userdata contains the request ID
      val requestId = userdata.toLong

      requestBuffers.get(requestId) match
        case Some(buffer) =>
          // Copy bytes directly into this request's buffer
          var i = 0
          while i < realsizeInt do
            buffer += !(contents + i)
            i += 1
        case None =>
          // Request not found, should not happen
          sys.error("writeCallback: request not found")

    realsize
  }

def fetch(url: String): HttpResponse =
  Zone:
    val handle = LibCurl.curl_easy_init()
    if handle == null then throw new CurlException("Failed to initialize curl")
    val requestId = handle.toLong

    try
      val responseBuffer = ArrayBuffer[Byte]()

      requestBuffers(requestId) = responseBuffer

      // Set the URL
      val urlResult = LibCurl.curl_easy_setopt(handle, CURLOPT_URL, toCString(url))
      if urlResult != CURLE_OK then
        throw new CurlException(s"Failed to set URL: $url")

      // Set the write callback
      val callbackResult = LibCurl.curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, writeCallback)
      if callbackResult != CURLE_OK then
        throw new CurlException("Failed to set write callback")

      // Set the userdata (pointer to request ID)
      val dataResult = LibCurl.curl_easy_setopt(handle, CURLOPT_WRITEDATA, requestId.toCSSize)
      if dataResult != CURLE_OK then
        throw new CurlException("Failed to set write data")

      // Perform the request
      val performResult = LibCurl.curl_easy_perform(handle)

      // Get the HTTP status code
      val statusCode = stackalloc[CLong]()
      LibCurl.curl_easy_getinfo(handle, CURLINFO_RESPONSE_CODE, statusCode)

      HttpResponse(
        body = responseBuffer.toArray,
        statusCode = (!statusCode).toInt,
        success = performResult == CURLE_OK,
      )

    finally
      // Clean up this request's buffer and handle
      requestBuffers.remove(requestId)
      LibCurl.curl_easy_cleanup(handle)
