package io.github.edadma.libcurl

@main def run(): Unit =
  try
    println("Fetching from httpbin.org...")
    val response = fetch("http://httpbin.org/get")

    if response.success then
      println("SUCCESS!")
      println(s"Status Code: ${response.statusCode}")
      println(s"Response body: ${response.body}")
    else
      println("Request failed")

  catch
    case e: CurlException =>
      println(s"Curl error: ${e.getMessage}")
    case e: Exception =>
      println(s"Other error: ${e.getMessage}")
