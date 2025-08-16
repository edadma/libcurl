package io.github.edadma.libcurl

@main def run(): Unit =
  try
    println("Testing basic GET request...")
    val getResponse = fetch("https://httpbin.org/get")

    if getResponse.ok then
      println("SUCCESS!")
      println(s"Status Code: ${getResponse.status}")
      println(s"Response: ${getResponse.bodyAsString}")
    else
      println("GET request failed")

    println("\n" + "=" * 60 + "\n")

    println("Testing default User-Agent...")
    val defaultUAResponse = fetch("https://httpbin.org/headers")

    if defaultUAResponse.ok then
      println("SUCCESS!")
      println(s"Status Code: ${defaultUAResponse.status}")
      println(s"Response: ${defaultUAResponse.bodyAsString}")
    else
      println("Default User-Agent request failed")

    println("\n" + "=" * 60 + "\n")

    println("Testing GET with custom headers...")
    val headersResponse = fetch(
      "https://httpbin.org/headers",
      "GET",
      None,
      Map(
        "Authorization" -> "Bearer test-token-123",
        "Custom-Header" -> "test-value",
      ),
    )

    if headersResponse.ok then
      println("SUCCESS!")
      println(s"Status Code: ${headersResponse.status}")
      println(s"Response: ${headersResponse.bodyAsString}")
    else
      println("Headers request failed")

    println("\n" + "=" * 60 + "\n")

    println("Testing custom User-Agent override...")
    val customUAResponse = fetch(
      "https://httpbin.org/headers",
      "GET",
      None,
      Map("User-Agent" -> "MyApp/1.0.0"),
    )

    if customUAResponse.ok then
      println("SUCCESS!")
      println(s"Status Code: ${customUAResponse.status}")
      println(s"Response: ${customUAResponse.bodyAsString}")
    else
      println("Custom User-Agent request failed")

    println("\n" + "=" * 60 + "\n")

    println("Testing POST with JSON...")
    val jsonData     = """{"name": "John Doe", "email": "john@example.com", "age": 30}"""
    val postResponse = fetch(
      "https://httpbin.org/post",
      "POST",
      Some(jsonData),
      Map(
        "Content-Type"  -> "application/json",
        "Authorization" -> "Bearer api-key-456",
      ),
    )

    if postResponse.ok then
      println("SUCCESS!")
      println(s"Status Code: ${postResponse.status}")
      println(s"Response: ${postResponse.bodyAsString}")
    else
      println("POST request failed")

  catch
    case e: FetchException =>
      println(s"Curl error: ${e.getMessage}")
    case e: Exception =>
      println(s"Other error: ${e.getMessage}")
