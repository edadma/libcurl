package io.github.edadma.libcurl

@main def run(): Unit =
  try
    println("Testing text response...")
    val textResponse = fetch("http://httpbin.org/get")

    if textResponse.success then
      println("SUCCESS!")
      println(s"Status Code: ${textResponse.statusCode}")
      println(s"Body size: ${textResponse.body.length} bytes")
      println(s"Response as text: ${textResponse.bodyAsString}")
    else
      println("Text request failed")

    println("\n" + "=" * 50 + "\n")

    println("Testing binary response...")
    val binaryResponse = fetch("http://httpbin.org/bytes/100") // Returns 100 random bytes

    if binaryResponse.success then
      println("SUCCESS!")
      println(s"Status Code: ${binaryResponse.statusCode}")
      println(s"Body size: ${binaryResponse.body.length} bytes")
      println(s"First 10 bytes: ${binaryResponse.body.take(10).map(b => f"0x$b%02X").mkString(", ")}")

      // Try to decode as text (should be mostly garbage)
      println(s"As text (first 50 chars): ${binaryResponse.bodyAsString.take(50)}")
    else
      println("Binary request failed")

  catch
    case e: CurlException =>
      println(s"Curl error: ${e.getMessage}")
    case e: Exception =>
      println(s"Other error: ${e.getMessage}")
