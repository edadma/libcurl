package io.github.edadma.libcurl

import io.github.edadma.libcurl.extern.{Curl as curl, CURL, CURLcode, WriteCallback}
import scala.scalanative.unsafe._
import scala.scalanative.libc.stdlib._

case class HttpResponse(body: String, success: Boolean)

class CurlException(message: String) extends RuntimeException(message)

// Simple thread-local storage for response data
private var currentResponseData: String = ""

// Write callback that uses thread-local storage
private val writeCallback: WriteCallback =
  CFuncPtr4.fromScalaFunction { (contents, size, nmemb, userdata) =>
    val realsize    = size * nmemb
    val realsizeInt = realsize.toInt

    // Convert received bytes to string
    val dataBytes = new Array[Byte](realsizeInt)
    var i         = 0
    while i < realsizeInt do
      dataBytes(i) = !(contents + i)
      i += 1
    val data = new String(dataBytes, "UTF-8")
    currentResponseData += data

    realsize
  }

def fetch(url: String): HttpResponse =
  Zone:
    val handle = curl.curl_easy_init()
    if handle == null then
      throw new CurlException("Failed to initialize curl")

    try
      // Reset response data
      currentResponseData = ""

      // Set the URL
      val urlResult = curl.curl_easy_setopt(handle, 10002, toCString(url).asInstanceOf[Ptr[Byte]]) // CURLOPT_URL
      if urlResult != 0 then // CURLE_OK
        throw new CurlException(s"Failed to set URL: $url")

      // For now, let's try without the callback to test basic functionality
      // TODO: Add callback back once we figure out the casting issue

      // Perform the request (will output to stdout by default)
      val performResult = curl.curl_easy_perform(handle)

      HttpResponse(
        body = "Basic request completed - no response capture yet",
        success = performResult == 0, // CURLE_OK
      )

    finally
      curl.curl_easy_cleanup(handle)
