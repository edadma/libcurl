# libcurl - Scala Native HTTP Client

A simple, synchronous HTTP client for Scala Native using libcurl.

**Status: Early stage but functional** ⚠️

## Why This Exists

This facade provides a simple HTTP client for Scala Native using the widely-available libcurl library. It's designed for developers who need basic HTTP functionality with minimal dependencies in Scala Native applications.

## Features

✅ **Multiple HTTP methods** - GET, POST, PUT, DELETE support  
✅ **Custom headers** - Authorization, Content-Type, User-Agent, etc.  
✅ **Request bodies** - JSON, form data, plain text  
✅ **Binary + Text support** - Handles any response type (JSON, images, PDFs, etc.)  
✅ **Thread-safe** - Concurrent requests won't interfere  
✅ **HTTP status codes** - Proper 200, 404, 500, etc. handling  
✅ **Memory efficient** - Automatic buffer management and cleanup  
✅ **HTTPS support** - SSL/TLS works out of the box  
✅ **Minimal dependencies** - Just libcurl, available on most systems

## Quick Start

### Dependencies

**Add to build.sbt:**
```scala
libraryDependencies += "io.github.edadma" %%% "libcurl" % "0.0.3"
```

**Install libcurl development headers:**
```bash
# Ubuntu/Debian
sudo apt install libcurl4-openssl-dev

# macOS (usually already available)
brew install curl
```

### Usage

```scala
import io.github.edadma.libcurl.*

// Simple GET request
val response = fetch("https://api.github.com/users/octocat")

if response.success then
  println(s"Status: ${response.statusCode}")
  println(s"JSON: ${response.bodyAsString}")
else
  println("Request failed")

// GET with Bearer token authentication
val authResponse = fetch(
  "https://api.example.com/protected",
  headers = Map("Authorization" -> "Bearer your-token-here")
)

// POST JSON data
val jsonData = """{"name": "John", "email": "john@example.com"}"""
val postResponse = fetch(
  "https://api.example.com/users",
  "POST",
  Some(jsonData), 
  Map(
    "Content-Type" -> "application/json",
    "Authorization" -> "Bearer api-token"
  )
)

// Binary data (images, files, etc.)
val imageResponse = fetch("https://example.com/photo.jpg")
if imageResponse.success then
  val bytes = imageResponse.body  // Array[Byte]
  // Save to file, process, etc.
```

### API

```scala
case class HttpResponse(
  body: Array[Byte],          // Raw response data
  statusCode: Int,            // HTTP status (200, 404, etc.)
  success: Boolean            // Whether request completed
):
  def bodyAsString: String                  // UTF-8 string
  def bodyAsString(charset: String): String // Custom charset

def fetch(
  url: String,
  method: String = "GET",               // HTTP method
  body: Option[String] = None,          // Request body  
  headers: Map[String, String] = Map.empty  // Custom headers
): HttpResponse
```

## Current Limitations

This is still an **early stage** facade with some limitations:

- ❌ **No response headers** - Only status code captured, headers not parsed yet
- ❌ **No authentication helpers** - Must manually set Authorization headers
- ❌ **No timeout configuration** - Uses libcurl defaults
- ❌ **No redirect control** - Uses libcurl defaults
- ❌ **No SSL options** - Uses system defaults
- ❌ **No file upload helpers** - No multipart/form-data support yet
- ❌ **No cookie support** - No session/cookie management

## Roadmap

Planned features for future versions:

- **Response headers** - Access to all response headers and metadata
- **Authentication helpers** - Built-in support for Basic auth, OAuth flows
- **File uploads** - Multipart form data support
- **Timeouts** - Connection and read timeout configuration
- **Error handling** - Better error messages and types
- **SSL options** - Certificate validation, custom CA
- **Advanced options** - Redirects, compression, cookies, sessions
- **Async support** - Future-based API for non-blocking requests

## Technical Details

### Thread Safety

Each `fetch()` call gets its own response buffer using the curl handle address as a unique key. Multiple concurrent requests won't interfere with each other.

### Memory Management

Uses Scala Native's `Zone` for automatic memory cleanup and `ArrayBuffer` for efficient binary data collection during transfers.

### Binary Support

All responses are captured as `Array[Byte]` first, then optionally converted to strings. This handles any content type correctly - text, JSON, images, etc.

## Build Configuration

```sbt
// build.sbt
enablePlugins(ScalaNativePlugin)

// libcurl should link automatically
// If not, you may need: nativeLinkingOptions += "-lcurl"
```

## Contributing

This is a working foundation that can be extended. Areas where help would be appreciated:

- **POST/PUT support** with request bodies
- **Header parsing** for request and response headers
- **Better error handling** with proper curl error code mapping
- **Testing** with various endpoints and edge cases
- **Documentation** improvements and examples

## License

ISC License - see LICENSE file.

## Alternatives

For JVM or Scala.js projects, you might consider:

- **sttp** - Full-featured HTTP client with extensive backend support
- **http4s** - Functional HTTP library with streaming and effect system integration
- **akka-http** - Reactive HTTP toolkit with actor-based concurrency

This libcurl facade is specifically designed for Scala Native environments where you need simple, synchronous HTTP requests with minimal overhead.