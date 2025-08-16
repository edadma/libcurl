package io.github.edadma.libcurl

import io.github.edadma.libcurl.extern.{LibCurl, CURLcode, WriteCallback}
import scala.scalanative.unsafe._

case class HttpResponse(body: String, statusCode: Int, success: Boolean)

class CurlException(message: String) extends RuntimeException(message)

// Curl option constants
val CURLOPT_URL: CInt           = 10002
val CURLOPT_WRITEFUNCTION: CInt = 20011
val CURLOPT_WRITEDATA: CInt     = 10001

// Curl info constants
val CURLINFO_RESPONSE_CODE: CInt = 2097154

// Curl error codes
val CURLE_OK: CURLcode = 0

// Simple thread-local storage for response data
private var currentResponseData = new StringBuilder

// Write callback that uses thread-local storage
private val writeCallback: WriteCallback =
  CFuncPtr4.fromScalaFunction: (contents, size, nmemb, userdata) =>
    val realsize    = size * nmemb
    val realsizeInt = realsize.toInt

    // Convert received bytes to string
    val dataBytes = new Array[Byte](realsizeInt)
    var i         = 0
    while i < realsizeInt do
      dataBytes(i) = !(contents + i)
      i += 1
    val data = new String(dataBytes, "UTF-8")
    currentResponseData ++= data

    realsize

def fetch(url: String): HttpResponse =
  Zone:
    val handle = LibCurl.curl_easy_init()
    if handle == null then
      throw new CurlException("Failed to initialize curl")

    try
      // Reset response data
      currentResponseData.clear

      // Set the URL
      val urlResult = LibCurl.curl_easy_setopt(handle, CURLOPT_URL, toCString(url))
      if urlResult != CURLE_OK then
        throw new CurlException(s"Failed to set URL: $url")

      // Set the write callback
      val callbackResult = LibCurl.curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, writeCallback)
      if callbackResult != CURLE_OK then
        throw new CurlException("Failed to set write callback")

      // Perform the request
      val performResult = LibCurl.curl_easy_perform(handle)

      // Get the HTTP status code
      val statusCode = stackalloc[CLong]()
      LibCurl.curl_easy_getinfo(handle, CURLINFO_RESPONSE_CODE, statusCode)

      HttpResponse(
        body = currentResponseData.toString,
        statusCode = (!statusCode).toInt,
        success = performResult == CURLE_OK,
      )

    finally
      LibCurl.curl_easy_cleanup(handle)
