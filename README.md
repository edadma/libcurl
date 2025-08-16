# libcurl - Scala Native HTTP Client

A simple, synchronous HTTP client for Scala Native using libcurl.

**Status: Early stage but functional** ⚠️

## Why This Exists

This facade provides a simple HTTP client for Scala Native using the widely-available libcurl library. It's designed for developers who need basic HTTP functionality with minimal dependencies in Scala Native applications.

## Features

✅ **Synchronous HTTP requests** - Simple `fetch(url)` API  
✅ **Binary + Text support** - Handles any response type (JSON, images, PDFs, etc.)  
✅ **Thread-safe** - Concurrent requests won't interfere  
✅ **HTTP status codes** - Proper 200, 404, 500, etc. handling  
✅ **Memory efficient** - Automatic buffer management and cleanup  
✅ **Minimal dependencies** - Just libcurl, available on most systems

## Quick Start

### Dependencies

**Add to build.sbt:**
```scala
libraryDependencies += "io.github.edadma" %%% "libcurl" % "0.0.2"
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

def fetch(url: String): HttpResponse
```

## Current Limitations

This is an **early stage** facade focused on basic functionality:

- ❌ **GET requests only** - No POST, PUT, DELETE yet
- ❌ **No custom headers** - Uses libcurl defaults
- ❌ **No authentication** - Basic auth, Bearer tokens not supported
- ❌ **No timeouts** - Uses libcurl defaults
- ❌ **No redirect control** - Uses libcurl defaults
- ❌ **No SSL options** - Uses system defaults
- ❌ **No response headers** - Only status code captured

## Roadmap

Planned features for future versions:

- **HTTP methods** - POST, PUT, DELETE with request bodies
- **Request headers** - Custom headers, authentication
- **Response headers** - Access to all response headers
- **Timeouts** - Connection and read timeout configuration
- **Error handling** - Better error messages and types
- **SSL options** - Certificate validation, custom CA
- **Advanced options** - Redirects, compression, cookies

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