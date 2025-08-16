package io.github.edadma.libcurl

import io.github.edadma.libcurl.extern.{LibCurl, CURL, CURLcode, WriteCallback, curl_slist}
import scala.scalanative.unsafe._
import scala.collection.mutable.ArrayBuffer
import scala.collection.concurrent.TrieMap

// Response class with binary support
case class HttpResponse(body: Array[Byte], status: Int, ok: Boolean):
  def bodyAsString: String                  = new String(body, "UTF-8")
  def bodyAsString(charset: String): String = new String(body, charset)

class FetchException(message: String) extends RuntimeException(message)

// Curl option constants
val CURLOPT_URL: CInt           = 10002
val CURLOPT_WRITEFUNCTION: CInt = 20011
val CURLOPT_WRITEDATA: CInt     = 10001
val CURLOPT_HTTPHEADER: CInt    = 10023
val CURLOPT_POSTFIELDS: CInt    = 10015

// Curl info constants
val CURLINFO_RESPONSE_CODE: CInt = 2097154

// Curl error codes
val CURLE_OK: CURLcode = 0

// Library version info
private val DEFAULT_USER_AGENT = s"${BuildInfo.organization}.${BuildInfo.name}/${BuildInfo.version}"

// Per-request response buffers using handle address as key (thread-safe)
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
          ()

    realsize
  }

def fetch(
    url: String,
    method: String = "GET",
    body: Option[String] = None,
    headers: Map[String, String] = Map.empty,
): HttpResponse =
  Zone:
    val handle = LibCurl.curl_easy_init()
    if handle == null then throw new FetchException("Failed to initialize curl")
    val requestId = handle.toLong

    // Declare headerList outside try block for cleanup access
    var headerList: curl_slist = null

    try
      val responseBuffer = ArrayBuffer[Byte]()
      requestBuffers(requestId) = responseBuffer

      // Set the URL
      val urlResult = LibCurl.curl_easy_setopt(handle, CURLOPT_URL, toCString(url).asInstanceOf[Ptr[Byte]])
      if urlResult != CURLE_OK then
        throw new FetchException(s"Failed to set URL: $url")

      // Set the write callback
      val callbackResult = LibCurl.curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, writeCallback)
      if callbackResult != CURLE_OK then
        throw new FetchException("Failed to set write callback")

      // Set the userdata (pointer to request ID)
      val dataResult = LibCurl.curl_easy_setopt(handle, CURLOPT_WRITEDATA, requestId.toCSSize)
      if dataResult != CURLE_OK then
        throw new FetchException("Failed to set write data")

      // Add default User-Agent if not provided by user
      val finalHeaders =
        if headers.contains("User-Agent") then headers
        else headers + ("User-Agent" -> DEFAULT_USER_AGENT)

      // Set custom headers if provided
      if finalHeaders.nonEmpty then
        for (key, value) <- finalHeaders do
          headerList = LibCurl.curl_slist_append(headerList, toCString(s"$key: $value"))

        val headerResult = LibCurl.curl_easy_setopt(handle, CURLOPT_HTTPHEADER, headerList)
        if headerResult != CURLE_OK then
          throw new FetchException("Failed to set headers")

      // Set POST data if provided
      body.foreach { bodyData =>
        val postResult =
          LibCurl.curl_easy_setopt(handle, CURLOPT_POSTFIELDS, toCString(bodyData))
        if postResult != CURLE_OK then
          throw new FetchException("Failed to set POST data")
      }

      // Perform the request
      val performResult = LibCurl.curl_easy_perform(handle)

      // Get the HTTP status code
      val statusCode = stackalloc[CLong]()
      LibCurl.curl_easy_getinfo(handle, CURLINFO_RESPONSE_CODE, statusCode)

      val statusInt = (!statusCode).toInt
      HttpResponse(
        body = responseBuffer.toArray,
        status = statusInt,
        ok = statusInt >= 200 && statusInt < 300,
      )
    finally
      // Clean up header list if created
      if headerList != null then
        LibCurl.curl_slist_free_all(headerList)

      // Clean up this request's buffer and handle
      requestBuffers.remove(requestId)
      LibCurl.curl_easy_cleanup(handle)
