package io.github.edadma

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*
//import scala.scalanative.runtime.Intrinsics.castLongToRawPtr
//import scala.scalanative.runtime.{Intrinsics, fromRawPtr}
import scala.collection.mutable

package object libcurl:

  import extern.{LibCurl => lib}

  final val GLOBAL_SSL = 1L << 0
  final val GLOBAL_WIN32 = 1L << 1
  final val GLOBAL_ALL = GLOBAL_SSL | GLOBAL_WIN32
  final val GLOBAL_NOTHING = 0L
  final val GLOBAL_DEFAULT = GLOBAL_ALL
  final val GLOBAL_ACK_EINTR = 1L << 2

  implicit class Code(val value: lib.CURLcode) extends AnyVal

  object Code:
    final val OK = new Code(0)
    final val UNSUPPORTED_PROTOCOL = new Code(1)
    final val FAILED_INIT = new Code(2)
    final val URL_MALFORMAT = new Code(3)
    final val NOT_BUILT_IN = new Code(4)
    final val COULDNT_RESOLVE_PROXY = new Code(5)
    final val COULDNT_RESOLVE_HOST = new Code(6)
    final val COULDNT_CONNECT = new Code(7)
    final val WEIRD_SERVER_REPLY = new Code(8)
    final val REMOTE_ACCESS_DENIED = new Code(9)
    final val FTP_ACCEPT_FAILED = new Code(10)
    final val FTP_WEIRD_PASS_REPLY = new Code(11)
    final val FTP_ACCEPT_TIMEOUT = new Code(12)
    final val FTP_WEIRD_PASV_REPLY = new Code(13)
    final val FTP_WEIRD_227_FORMAT = new Code(14)
    final val FTP_CANT_GET_HOST = new Code(15)
    final val HTTP2 = new Code(16)
    final val FTP_COULDNT_SET_TYPE = new Code(17)
    final val PARTIAL_FILE = new Code(18)
    final val FTP_COULDNT_RETR_FILE = new Code(19)
    final val OBSOLETE20 = new Code(20)
    final val QUOTE_ERROR = new Code(21)
    final val HTTP_RETURNED_ERROR = new Code(22)
    final val WRITE_ERROR = new Code(23)
    final val OBSOLETE24 = new Code(24)
    final val UPLOAD_FAILED = new Code(25)
    final val READ_ERROR = new Code(26)
    final val OUT_OF_MEMORY = new Code(27)
    final val OPERATION_TIMEDOUT = new Code(28)
    final val OBSOLETE29 = new Code(29)
    final val FTP_PORT_FAILED = new Code(30)
    final val FTP_COULDNT_USE_REST = new Code(31)
    final val OBSOLETE32 = new Code(32)
    final val RANGE_ERROR = new Code(33)
    final val HTTP_POST_ERROR = new Code(34)
    final val SSL_CONNECT_ERROR = new Code(35)
    final val BAD_DOWNLOAD_RESUME = new Code(36)
    final val FILE_COULDNT_READ_FILE = new Code(37)
    final val LDAP_CANNOT_BIND = new Code(38)
    final val LDAP_SEARCH_FAILED = new Code(39)
    final val OBSOLETE40 = new Code(40)
    final val FUNCTION_NOT_FOUND = new Code(41)
    final val ABORTED_BY_CALLBACK = new Code(42)
    final val BAD_FUNCTION_ARGUMENT = new Code(43)
    final val OBSOLETE44 = new Code(44)
    final val INTERFACE_FAILED = new Code(45)
    final val OBSOLETE46 = new Code(46)
    final val TOO_MANY_REDIRECTS = new Code(47)
    final val UNKNOWN_OPTION = new Code(48)
    final val SETOPT_OPTION_SYNTAX = new Code(49)
    final val OBSOLETE50 = new Code(50)
    final val OBSOLETE51 = new Code(51)
    final val GOT_NOTHING = new Code(52)
    final val SSL_ENGINE_NOTFOUND = new Code(53)
    final val SSL_ENGINE_SETFAILED = new Code(54)
    final val SEND_ERROR = new Code(55)
    final val RECV_ERROR = new Code(56)
    final val OBSOLETE57 = new Code(57)
    final val SSL_CERTPROBLEM = new Code(58)
    final val SSL_CIPHER = new Code(59)
    final val PEER_FAILED_VERIFICATION = new Code(60)
    final val BAD_CONTENT_ENCODING = new Code(61)
    final val LDAP_INVALID_URL = new Code(62)
    final val FILESIZE_EXCEEDED = new Code(63)
    final val USE_SSL_FAILED = new Code(64)
    final val SEND_FAIL_REWIND = new Code(65)
    final val SSL_ENGINE_INITFAILED = new Code(66)
    final val LOGIN_DENIED = new Code(67)
    final val TFTP_NOTFOUND = new Code(68)
    final val TFTP_PERM = new Code(69)
    final val REMOTE_DISK_FULL = new Code(70)
    final val TFTP_ILLEGAL = new Code(71)
    final val TFTP_UNKNOWNID = new Code(72)
    final val REMOTE_FILE_EXISTS = new Code(73)
    final val TFTP_NOSUCHUSER = new Code(74)
    final val CONV_FAILED = new Code(75)
    final val CONV_REQD = new Code(76)
    final val SSL_CACERT_BADFILE = new Code(77)
    final val REMOTE_FILE_NOT_FOUND = new Code(78)
    final val SSH = new Code(79)
    final val SSL_SHUTDOWN_FAILED = new Code(80)
    final val AGAIN = new Code(81)
    final val SSL_CRL_BADFILE = new Code(82)
    final val SSL_ISSUER_ERROR = new Code(83)
    final val FTP_PRET_FAILED = new Code(84)
    final val RTSP_CSEQ_ERROR = new Code(85)
    final val RTSP_SESSION_ERROR = new Code(86)
    final val FTP_BAD_FILE_LIST = new Code(87)
    final val CHUNK_FAILED = new Code(88)
    final val NO_CONNECTION_AVAILABLE = new Code(89)
    final val SSL_PINNEDPUBKEYNOTMATCH = new Code(90)
    final val SSL_INVALIDCERTSTATUS = new Code(91)
    final val HTTP2_STREAM = new Code(92)
    final val RECURSIVE_API_CALL = new Code(93)
    final val AUTH_ERROR = new Code(94)
    final val HTTP3 = new Code(95)
    final val QUIC_CONNECT_ERROR = new Code(96)
    final val PROXY = new Code(97)
    final val SSL_CLIENTCERT = new Code(98)

  implicit class MCode(val value: lib.CURLMcode) extends AnyVal

  object MCode:
    final val CALL_MULTI_PERFORM = MCode(-1) /* please call curl_multi_perform() or curl_multi_socket*() soon */
    final val OK = MCode(0)
    final val BAD_HANDLE = MCode(1)      /* the passed-in handle is not a valid CURLM handle */
    final val BAD_EASY_HANDLE = MCode(2) /* an easy handle was not good/valid */
    final val OUT_OF_MEMORY = MCode(3)   /* if you ever get this, you're in deep sh*t */
    final val INTERNAL_ERROR = MCode(4)  /* this is a libcurl bug */
    final val BAD_SOCKET = MCode(5)      /* the passed in socket argument did not match */
    final val UNKNOWN_OPTION = MCode(6)  /* curl_multi_setopt() with unsupported option */
    final val ADDED_ALREADY = MCode(7)   /* an easy handle already added to a multi handle was attempted to get added - again */
    final val RECURSIVE_API_CALL = MCode(8) /* an api function was called from inside a callback */
    final val WAKEUP_FAILURE = MCode(9)  /* wakeup is unavailable or failed */
    final val BAD_FUNCTION_ARGUMENT = MCode(10) /* function called with a bad parameter */
    final val ABORTED_BY_CALLBACK = MCode(11)

  implicit class CurlMOption(val value: lib.CURLMoption) extends AnyVal

  object CurlMOption:
    /* This is the socket callback function pointer */
    final val CURLMOPT_SOCKETFUNCTION = new CurlMOption(CURLOPTTYPE_FUNCTIONPOINT + 1)

    /* This is the argument passed to the socket callback */
    final val CURLMOPT_SOCKETDATA = new CurlMOption(CURLOPTTYPE_OBJECTPOINT + 2)

      /* set to 1 to enable pipelining for this multi handle */
    final val CURLMOPT_PIPELINING = new CurlMOption(CURLOPTTYPE_LONG + 3)

     /* This is the timer callback function pointer */
    final val CURLMOPT_TIMERFUNCTION = new CurlMOption(CURLOPTTYPE_FUNCTIONPOINT + 4)

    /* This is the argument passed to the timer callback */
    final val CURLMOPT_TIMERDATA = new CurlMOption(CURLOPTTYPE_OBJECTPOINT + 5)

    /* maximum number of entries in the connection cache */
    final val CURLMOPT_MAXCONNECTS = new CurlMOption(CURLOPTTYPE_LONG + 6)

    /* maximum number of (pipelining) connections to one host */
    final val CURLMOPT_MAX_HOST_CONNECTIONS = new CurlMOption(CURLOPTTYPE_LONG + 7)

    /* maximum number of requests in a pipeline */
    final val CURLMOPT_MAX_PIPELINE_LENGTH = new CurlMOption(CURLOPTTYPE_LONG + 8)

    /* a connection with a content-length longer than this
       will not be considered for pipelining */
    final val CURLMOPT_CONTENT_LENGTH_PENALTY_SIZE = new CurlMOption(CURLOPTTYPE_OFF_T + 9)

    /* a connection with a chunk length longer than this
       will not be considered for pipelining */
    final val CURLMOPT_CHUNK_LENGTH_PENALTY_SIZE = new CurlMOption(CURLOPTTYPE_OFF_T + 10)

    /* a list of site names(+port) that are blocked from pipelining */
    final val CURLMOPT_PIPELINING_SITE_BL = new CurlMOption(CURLOPTTYPE_OBJECTPOINT + 11)

    /* a list of server types that are blocked from pipelining */
    final val CURLMOPT_PIPELINING_SERVER_BL = new CurlMOption(CURLOPTTYPE_OBJECTPOINT + 12)

    /* maximum number of open connections in total */
    final val CURLMOPT_MAX_TOTAL_CONNECTIONS = new CurlMOption(CURLOPTTYPE_LONG + 13)

     /* This is the server push callback function pointer */
    final val CURLMOPT_PUSHFUNCTION = new CurlMOption(CURLOPTTYPE_FUNCTIONPOINT + 14)

    /* This is the argument passed to the server push callback */
    final val CURLMOPT_PUSHDATA = new CurlMOption(CURLOPTTYPE_OBJECTPOINT + 15)

    /* maximum number of concurrent streams to support on a connection */
    final val CURLMOPT_MAX_CONCURRENT_STREAMS = new CurlMOption(CURLOPTTYPE_LONG + 16)

  def globalInit(flags: Long): Code = lib.curl_global_init(flags)

  implicit class CurlOption(val value: lib.CURLoption) extends AnyVal

  val CURLOPTTYPE_LONG = 0
  val CURLOPTTYPE_OBJECTPOINT = 10000
  val CURLOPTTYPE_FUNCTIONPOINT = 20000
  val CURLOPTTYPE_OFF_T = 30000
  val CURLOPTTYPE_BLOB = 40000

  /* 'char *' argument to a string with a trailing zero */
  val CURLOPTTYPE_STRINGPOINT = CURLOPTTYPE_OBJECTPOINT

  /* 'struct curl_slist *' argument */
  val CURLOPTTYPE_SLISTPOINT = CURLOPTTYPE_OBJECTPOINT

  /* 'void *' argument passed untouched to callback */
  val CURLOPTTYPE_CBPOINT = CURLOPTTYPE_OBJECTPOINT

  /* 'long' argument with a set of values/bitmask */
  val CURLOPTTYPE_VALUES = CURLOPTTYPE_LONG

  object CurlOption:
    /* This is the FILE * or void * the regular output should be written to. */
    final val WRITEDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 1)

    /* The full URL to get/put */
    final val URL = new CurlOption(CURLOPTTYPE_STRINGPOINT + 2)

    /* Port number to connect to, if other than default. */
    final val PORT = new CurlOption(CURLOPTTYPE_LONG + 3)

    /* Name of proxy to use. */
    final val PROXY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 4)

    /* "user:password;options" to use when fetching. */
    final val USERPWD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 5)

    /* "user:password" to use with proxy. */
    final val PROXYUSERPWD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 6)

    /* Range to get, specified as an ASCII string. */
    final val RANGE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 7)

    /* not used */

    /* Specified file stream to upload from (use as input): */
    final val READDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 9)

    /* Buffer to receive error messages in, must be at least CURL_ERROR_SIZE
     * bytes big. */
    final val ERRORBUFFER = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 10)

    /* Function that will be called to store the output (instead of fwrite). The
     * parameters will use fwrite() syntax, make sure to follow them. */
    final val WRITEFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 11)

    /* Function that will be called to read the input (instead of fread). The
     * parameters will use fread() syntax, make sure to follow them. */
    final val READFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 12)

    /* Time-out the read operation after this amount of seconds */
    final val TIMEOUT = new CurlOption(CURLOPTTYPE_LONG + 13)

    /* If the CURLOPT_INFILE is used, this can be used to inform libcurl about
     * how large the file being sent really is. That allows better error
     * checking and better verifies that the upload was successful. -1 means
     * unknown size.
     *
     * For large file support, there is also a _LARGE version of the key
     * which takes an off_t type, allowing platforms with larger off_t
     * sizes to handle larger files.  See below for INFILESIZE_LARGE.
     */
    final val INFILESIZE = new CurlOption(CURLOPTTYPE_LONG + 14)

    /* POST static input fields. */
    final val POSTFIELDS = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 15)

    /* Set the referrer page (needed by some CGIs) */
    final val REFERER = new CurlOption(CURLOPTTYPE_STRINGPOINT + 16)

    /* Set the FTP PORT string (interface name, named or numerical IP address)
       Use i.e '-' to use default address. */
    final val FTPPORT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 17)

    /* Set the User-Agent string (examined by some CGIs) */
    final val USERAGENT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 18)

    /* If the download receives less than "low speed limit" bytes/second
     * during "low speed time" seconds, the operations is aborted.
     * You could i.e if you have a pretty high speed connection, abort if
     * it is less than 2000 bytes/sec during 20 seconds.
     */

    /* Set the "low speed limit" */
    final val LOW_SPEED_LIMIT = new CurlOption(CURLOPTTYPE_LONG + 19)

    /* Set the "low speed time" */
    final val LOW_SPEED_TIME = new CurlOption(CURLOPTTYPE_LONG + 20)

    /* Set the continuation offset.
     *
     * Note there is also a _LARGE version of this key which uses
     * off_t types, allowing for large file offsets on platforms which
     * use larger-than-32-bit off_t's.  Look below for RESUME_FROM_LARGE.
     */
    final val RESUME_FROM = new CurlOption(CURLOPTTYPE_LONG + 21)

    /* Set cookie in request: */
    final val COOKIE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 22)

    /* This points to a linked list of headers, struct curl_slist kind. This
       list is also used for RTSP (in spite of its name) */
    final val HTTPHEADER = new CurlOption(CURLOPTTYPE_SLISTPOINT + 23)

    /* This points to a linked list of post entries, struct curl_httppost */
    final val HTTPPOST = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 24)

    /* name of the file keeping your private SSL-certificate */
    final val SSLCERT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 25)

    /* password for the SSL or SSH private key */
    final val KEYPASSWD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 26)

    /* send TYPE parameter? */
    final val CRLF = new CurlOption(CURLOPTTYPE_LONG + 27)

    /* send linked-list of QUOTE commands */
    final val QUOTE = new CurlOption(CURLOPTTYPE_SLISTPOINT + 28)

    /* send FILE * or void * to store headers to, if you use a callback it
       is simply passed to the callback unmodified */
    final val HEADERDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 29)

    /* point to a file to read the initial cookies from, also enables
       "cookie awareness" */
    final val COOKIEFILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 31)

    /* What version to specifically try to use.
       See CURL_SSLVERSION defines below. */
    final val SSLVERSION = new CurlOption(CURLOPTTYPE_VALUES + 32)

    /* What kind of HTTP time condition to use, see defines */
    final val TIMECONDITION = new CurlOption(CURLOPTTYPE_VALUES + 33)

    /* Time to use with the above condition. Specified in number of seconds
       since 1 Jan 1970 */
    final val TIMEVALUE = new CurlOption(CURLOPTTYPE_LONG + 34)

    /* 35 = OBSOLETE */

    /* Custom request, for customizing the get command like
       HTTP: DELETE, TRACE and others
       FTP: to use a different list command
     */
    final val CUSTOMREQUEST = new CurlOption(CURLOPTTYPE_STRINGPOINT + 36)

    /* FILE handle to use instead of stderr */
    final val STDERR = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 37)

    /* 38 is not used */

    /* send linked-list of post-transfer QUOTE commands */
    final val POSTQUOTE = new CurlOption(CURLOPTTYPE_SLISTPOINT + 39)

    /* talk a lot */
    final val VERBOSE = new CurlOption(CURLOPTTYPE_LONG + 41)

    /* throw the header out too */
    final val HEADER = new CurlOption(CURLOPTTYPE_LONG + 42)

    /* shut off the progress meter */
    final val NOPROGRESS = new CurlOption(CURLOPTTYPE_LONG + 43)

    /* use HEAD to get http document */
    final val NOBODY = new CurlOption(CURLOPTTYPE_LONG + 44)

    /* no output on http error codes >= 400 */
    final val FAILONERROR = new CurlOption(CURLOPTTYPE_LONG + 45)

    /* this is an upload */
    final val UPLOAD = new CurlOption(CURLOPTTYPE_LONG + 46)

    /* HTTP POST method */
    final val POST = new CurlOption(CURLOPTTYPE_LONG + 47)

    /* bare names when listing directories */
    final val DIRLISTONLY = new CurlOption(CURLOPTTYPE_LONG + 48)

    /* Append instead of overwrite on upload! */
    final val APPEND = new CurlOption(CURLOPTTYPE_LONG + 50)

    /* Specify whether to read the user+password from the .netrc or the URL.
     * This must be one of the CURL_NETRC_* enums below. */
    final val NETRC = new CurlOption(CURLOPTTYPE_VALUES + 51)

    /* use Location: Luke! */
    final val FOLLOWLOCATION = new CurlOption(CURLOPTTYPE_LONG + 52)

    /* transfer data in text/ASCII format */
    final val TRANSFERTEXT = new CurlOption(CURLOPTTYPE_LONG + 53)

    /* HTTP PUT */
    final val PUT = new CurlOption(CURLOPTTYPE_LONG + 54)

    /* 55 = OBSOLETE */

    /* DEPRECATED
     * Function that will be called instead of the internal progress display
     * function. This function should be defined as the curl_progress_callback
     * prototype defines. */
    final val PROGRESSFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 56)

    /* Data passed to the CURLOPT_PROGRESSFUNCTION and CURLOPT_XFERINFOFUNCTION
       callbacks */
    final val XFERINFODATA = new CurlOption(CURLOPTTYPE_CBPOINT + 57)

    /* We want the referrer field set automatically when following locations */
    final val AUTOREFERER = new CurlOption(CURLOPTTYPE_LONG + 58)

    /* Port of the proxy, can be set in the proxy string as well with:
       "[host]:[port]" */
    final val PROXYPORT = new CurlOption(CURLOPTTYPE_LONG + 59)

    /* size of the POST input data, if strlen() is not good to use */
    final val POSTFIELDSIZE = new CurlOption(CURLOPTTYPE_LONG + 60)

    /* tunnel non-http operations through a HTTP proxy */
    final val HTTPPROXYTUNNEL = new CurlOption(CURLOPTTYPE_LONG + 61)

    /* Set the interface string to use as outgoing network interface */
    final val INTERFACE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 62)

    /* Set the krb4/5 security level, this also enables krb4/5 awareness.  This
     * is a string, 'clear', 'safe', 'confidential' or 'private'.  If the string
     * is set but doesn't match one of these, 'private' will be used.  */
    final val KRBLEVEL = new CurlOption(CURLOPTTYPE_STRINGPOINT + 63)

    /* Set if we should verify the peer in ssl handshake, set 1 to verify. */
    final val SSL_VERIFYPEER = new CurlOption(CURLOPTTYPE_LONG + 64)

    /* The CApath or CAfile used to validate the peer certificate
       this option is used only if SSL_VERIFYPEER is true */
    final val CAINFO = new CurlOption(CURLOPTTYPE_STRINGPOINT + 65)

    /* 66 = OBSOLETE */
    /* 67 = OBSOLETE */

    /* Maximum number of http redirects to follow */
    final val MAXREDIRS = new CurlOption(CURLOPTTYPE_LONG + 68)

    /* Pass a long set to 1 to get the date of the requested document (if
       possible)! Pass a zero to shut it off. */
    final val FILETIME = new CurlOption(CURLOPTTYPE_LONG + 69)

    /* This points to a linked list of telnet options */
    final val TELNETOPTIONS = new CurlOption(CURLOPTTYPE_SLISTPOINT + 70)

    /* Max amount of cached alive connections */
    final val MAXCONNECTS = new CurlOption(CURLOPTTYPE_LONG + 71)

    /* Set to explicitly use a new connection for the upcoming transfer.
       Do not use this unless you're absolutely sure of this, as it makes the
       operation slower and is less friendly for the network. */
    final val FRESH_CONNECT = new CurlOption(CURLOPTTYPE_LONG + 74)

    /* Set to explicitly forbid the upcoming transfer's connection to be re-used
       when done. Do not use this unless you're absolutely sure of this, as it
       makes the operation slower and is less friendly for the network. */
    final val FORBID_REUSE = new CurlOption(CURLOPTTYPE_LONG + 75)

    /* Set to a file name that contains random data for libcurl to use to
       seed the random engine when doing SSL connects. */
    final val RANDOM_FILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 76)

    /* Set to the Entropy Gathering Daemon socket pathname */
    final val EGDSOCKET = new CurlOption(CURLOPTTYPE_STRINGPOINT + 77)

    /* Time-out connect operations after this amount of seconds, if connects are
       OK within this time, then fine... This only aborts the connect phase. */
    final val CONNECTTIMEOUT = new CurlOption(CURLOPTTYPE_LONG + 78)

    /* Function that will be called to store headers (instead of fwrite). The
     * parameters will use fwrite() syntax, make sure to follow them. */
    final val HEADERFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 79)

    /* Set this to force the HTTP request to get back to GET. Only really usable
       if POST, PUT or a custom request have been used first.
     */
    final val HTTPGET = new CurlOption(CURLOPTTYPE_LONG + 80)

    /* Set if we should verify the Common name from the peer certificate in ssl
     * handshake, set 1 to check existence, 2 to ensure that it matches the
     * provided hostname. */
    final val SSL_VERIFYHOST = new CurlOption(CURLOPTTYPE_LONG + 81)

    /* Specify which file name to write all known cookies in after completed
       operation. Set file name to "-" (dash) to make it go to stdout. */
    final val COOKIEJAR = new CurlOption(CURLOPTTYPE_STRINGPOINT + 82)

    /* Specify which SSL ciphers to use */
    final val SSL_CIPHER_LIST = new CurlOption(CURLOPTTYPE_STRINGPOINT + 83)

    /* Specify which HTTP version to use! This must be set to one of the
       CURL_HTTP_VERSION* enums set below. */
    final val HTTP_VERSION = new CurlOption(CURLOPTTYPE_VALUES + 84)

    /* Specifically switch on or off the FTP engine's use of the EPSV command. By
       default, that one will always be attempted before the more traditional
       PASV command. */
    final val FTP_USE_EPSV = new CurlOption(CURLOPTTYPE_LONG + 85)

    /* type of the file keeping your SSL-certificate ("DER", "PEM", "ENG") */
    final val SSLCERTTYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 86)

    /* name of the file keeping your private SSL-key */
    final val SSLKEY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 87)

    /* type of the file keeping your private SSL-key ("DER", "PEM", "ENG") */
    final val SSLKEYTYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 88)

    /* crypto engine for the SSL-sub system */
    final val SSLENGINE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 89)

    /* set the crypto engine for the SSL-sub system as default
       the param has no meaning...
     */
    final val SSLENGINE_DEFAULT = new CurlOption(CURLOPTTYPE_LONG + 90)

    /* Non-zero value means to use the global dns cache */
    /* DEPRECATED, do not use! */
    final val DNS_USE_GLOBAL_CACHE = new CurlOption(CURLOPTTYPE_LONG + 91)

    /* DNS cache timeout */
    final val DNS_CACHE_TIMEOUT = new CurlOption(CURLOPTTYPE_LONG + 92)

    /* send linked-list of pre-transfer QUOTE commands */
    final val PREQUOTE = new CurlOption(CURLOPTTYPE_SLISTPOINT + 93)

    /* set the debug function */
    final val DEBUGFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 94)

    /* set the data for the debug function */
    final val DEBUGDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 95)

    /* mark this as start of a cookie session */
    final val COOKIESESSION = new CurlOption(CURLOPTTYPE_LONG + 96)

    /* The CApath directory used to validate the peer certificate
       this option is used only if SSL_VERIFYPEER is true */
    final val CAPATH = new CurlOption(CURLOPTTYPE_STRINGPOINT + 97)

    /* Instruct libcurl to use a smaller receive buffer */
    final val BUFFERSIZE = new CurlOption(CURLOPTTYPE_LONG + 98)

    /* Instruct libcurl to not use any signal/alarm handlers, even when using
       timeouts. This option is useful for multi-threaded applications.
       See libcurl-the-guide for more background information. */
    final val NOSIGNAL = new CurlOption(CURLOPTTYPE_LONG + 99)

    /* Provide a CURLShare for mutexing non-ts data */
    final val SHARE = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 100)

    /* indicates type of proxy. accepted values are CURLPROXY_HTTP (default),
       CURLPROXY_HTTPS, CURLPROXY_SOCKS4, CURLPROXY_SOCKS4A and
       CURLPROXY_SOCKS5. */
    final val PROXYTYPE = new CurlOption(CURLOPTTYPE_VALUES + 101)

    /* Set the Accept-Encoding string. Use this to tell a server you would like
       the response to be compressed. Before 7.21.6, this was known as
       CURLOPT_ENCODING */
    final val ACCEPT_ENCODING = new CurlOption(CURLOPTTYPE_STRINGPOINT + 102)

    /* Set pointer to private data */
    final val PRIVATE = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 103)

    /* Set aliases for HTTP 200 in the HTTP Response header */
    final val HTTP200ALIASES = new CurlOption(CURLOPTTYPE_SLISTPOINT + 104)

    /* Continue to send authentication (user+password) when following locations,
       even when hostname changed. This can potentially send off the name
       and password to whatever host the server decides. */
    final val UNRESTRICTED_AUTH = new CurlOption(CURLOPTTYPE_LONG + 105)

    /* Specifically switch on or off the FTP engine's use of the EPRT command (
       it also disables the LPRT attempt). By default, those ones will always be
       attempted before the good old traditional PORT command. */
    final val FTP_USE_EPRT = new CurlOption(CURLOPTTYPE_LONG + 106)

    /* Set this to a bitmask value to enable the particular authentications
       methods you like. Use this in combination with CURLOPT_USERPWD.
       Note that setting multiple bits may cause extra network round-trips. */
    final val HTTPAUTH = new CurlOption(CURLOPTTYPE_VALUES + 107)

    /* Set the ssl context callback function, currently only for OpenSSL or
       WolfSSL ssl_ctx, or mbedTLS mbedtls_ssl_config in the second argument.
       The function must match the curl_ssl_ctx_callback prototype. */
    final val SSL_CTX_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 108)

    /* Set the userdata for the ssl context callback function's third
       argument */
    final val SSL_CTX_DATA = new CurlOption(CURLOPTTYPE_CBPOINT + 109)

    /* FTP Option that causes missing dirs to be created on the remote server.
       In 7.19.4 we introduced the convenience enums for this option using the
       CURLFTP_CREATE_DIR prefix.
     */
    final val FTP_CREATE_MISSING_DIRS = new CurlOption(CURLOPTTYPE_LONG + 110)

    /* Set this to a bitmask value to enable the particular authentications
       methods you like. Use this in combination with CURLOPT_PROXYUSERPWD.
       Note that setting multiple bits may cause extra network round-trips. */
    final val PROXYAUTH = new CurlOption(CURLOPTTYPE_VALUES + 111)

    /* FTP option that changes the timeout, in seconds, associated with
       getting a response.  This is different from transfer timeout time and
       essentially places a demand on the FTP server to acknowledge commands
       in a timely manner. */
    final val FTP_RESPONSE_TIMEOUT = new CurlOption(CURLOPTTYPE_LONG + 112)

    /* Set this option to one of the CURL_IPRESOLVE_* defines (see below) to
       tell libcurl to use those IP versions only. This only has effect on
       systems with support for more than one, i.e IPv4 _and_ IPv6. */
    final val IPRESOLVE = new CurlOption(CURLOPTTYPE_VALUES + 113)

    /* Set this option to limit the size of a file that will be downloaded from
       an HTTP or FTP server.

       Note there is also _LARGE version which adds large file support for
       platforms which have larger off_t sizes.  See MAXFILESIZE_LARGE below. */
    final val MAXFILESIZE = new CurlOption(CURLOPTTYPE_LONG + 114)

    /* See the comment for INFILESIZE above, but in short, specifies
     * the size of the file being uploaded.  -1 means unknown.
     */
    final val INFILESIZE_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 115)

    /* Sets the continuation offset.  There is also a CURLOPTTYPE_LONG version
     * of this; look above for RESUME_FROM.
     */
    final val RESUME_FROM_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 116)

    /* Sets the maximum size of data that will be downloaded from
     * an HTTP or FTP server.  See MAXFILESIZE above for the LONG version.
     */
    final val MAXFILESIZE_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 117)

    /* Set this option to the file name of your .netrc file you want libcurl
       to parse (using the CURLOPT_NETRC option). If not set, libcurl will do
       a poor attempt to find the user's home directory and check for a .netrc
       file in there. */
    final val NETRC_FILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 118)

    /* Enable SSL/TLS for FTP, pick one of:
       CURLUSESSL_TRY     - try using SSL, proceed anyway otherwise
       CURLUSESSL_CONTROL - SSL for the control connection or fail
       CURLUSESSL_ALL     - SSL for all communication or fail
     */
    final val USE_SSL = new CurlOption(CURLOPTTYPE_VALUES + 119)

    /* The _LARGE version of the standard POSTFIELDSIZE option */
    final val POSTFIELDSIZE_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 120)

    /* Enable/disable the TCP Nagle algorithm */
    final val TCP_NODELAY = new CurlOption(CURLOPTTYPE_LONG + 121)

    /* 122 OBSOLETE, used in 7.12.3. Gone in 7.13.0 */
    /* 123 OBSOLETE. Gone in 7.16.0 */
    /* 124 OBSOLETE, used in 7.12.3. Gone in 7.13.0 */
    /* 125 OBSOLETE, used in 7.12.3. Gone in 7.13.0 */
    /* 126 OBSOLETE, used in 7.12.3. Gone in 7.13.0 */
    /* 127 OBSOLETE. Gone in 7.16.0 */
    /* 128 OBSOLETE. Gone in 7.16.0 */

    /* When FTP over SSL/TLS is selected (with CURLOPT_USE_SSL), this option
       can be used to change libcurl's default action which is to first try
       "AUTH SSL" and then "AUTH TLS" in this order, and proceed when a OK
       response has been received.

       Available parameters are:
       CURLFTPAUTH_DEFAULT - let libcurl decide
       CURLFTPAUTH_SSL     - try "AUTH SSL" first, then TLS
       CURLFTPAUTH_TLS     - try "AUTH TLS" first, then SSL
     */
    final val FTPSSLAUTH = new CurlOption(CURLOPTTYPE_VALUES + 129)

    final val IOCTLFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 130)
    final val IOCTLDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 131)

    /* 132 OBSOLETE. Gone in 7.16.0 */
    /* 133 OBSOLETE. Gone in 7.16.0 */

    /* null-terminated string for pass on to the FTP server when asked for
       "account" info */
    final val FTP_ACCOUNT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 134)

    /* feed cookie into cookie engine */
    final val COOKIELIST = new CurlOption(CURLOPTTYPE_STRINGPOINT + 135)

    /* ignore Content-Length */
    final val IGNORE_CONTENT_LENGTH = new CurlOption(CURLOPTTYPE_LONG + 136)

    /* Set to non-zero to skip the IP address received in a 227 PASV FTP server
       response. Typically used for FTP-SSL purposes but is not restricted to
       that. libcurl will then instead use the same IP address it used for the
       control connection. */
    final val FTP_SKIP_PASV_IP = new CurlOption(CURLOPTTYPE_LONG + 137)

    /* Select "file method" to use when doing FTP, see the curl_ftpmethod
       above. */
    final val FTP_FILEMETHOD = new CurlOption(CURLOPTTYPE_VALUES + 138)

    /* Local port number to bind the socket to */
    final val LOCALPORT = new CurlOption(CURLOPTTYPE_LONG + 139)

    /* Number of ports to try, including the first one set with LOCALPORT.
       Thus, setting it to 1 will make no additional attempts but the first.
     */
    final val LOCALPORTRANGE = new CurlOption(CURLOPTTYPE_LONG + 140)

    /* no transfer, set up connection and let application use the socket by
       extracting it with CURLINFO_LASTSOCKET */
    final val CONNECT_ONLY = new CurlOption(CURLOPTTYPE_LONG + 141)

    /* Function that will be called to convert from the
       network encoding (instead of using the iconv calls in libcurl) */
    final val CONV_FROM_NETWORK_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 142)

    /* Function that will be called to convert to the
       network encoding (instead of using the iconv calls in libcurl) */
    final val CONV_TO_NETWORK_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 143)

    /* Function that will be called to convert from UTF8
       (instead of using the iconv calls in libcurl)
       Note that this is used only for SSL certificate processing */
    final val CONV_FROM_UTF8_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 144)

    /* if the connection proceeds too quickly then need to slow it down */
    /* limit-rate: maximum number of bytes per second to send or receive */
    final val MAX_SEND_SPEED_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 145)
    final val MAX_RECV_SPEED_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 146)

    /* Pointer to command string to send if USER/PASS fails. */
    final val FTP_ALTERNATIVE_TO_USER = new CurlOption(CURLOPTTYPE_STRINGPOINT + 147)

    /* callback function for setting socket options */
    final val SOCKOPTFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 148)
    final val SOCKOPTDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 149)

    /* set to 0 to disable session ID re-use for this transfer, default is
       enabled (== 1) */
    final val SSL_SESSIONID_CACHE = new CurlOption(CURLOPTTYPE_LONG + 150)

    /* allowed SSH authentication methods */
    final val SSH_AUTH_TYPES = new CurlOption(CURLOPTTYPE_VALUES + 151)

    /* Used by scp/sftp to do public/private key authentication */
    final val SSH_PUBLIC_KEYFILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 152)
    final val SSH_PRIVATE_KEYFILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 153)

    /* Send CCC (Clear Command Channel) after authentication */
    final val FTP_SSL_CCC = new CurlOption(CURLOPTTYPE_LONG + 154)

    /* Same as TIMEOUT and CONNECTTIMEOUT, but with ms resolution */
    final val TIMEOUT_MS = new CurlOption(CURLOPTTYPE_LONG + 155)
    final val CONNECTTIMEOUT_MS = new CurlOption(CURLOPTTYPE_LONG + 156)

    /* set to zero to disable the libcurl's decoding and thus pass the raw body
       data to the application even when it is encoded/compressed */
    final val HTTP_TRANSFER_DECODING = new CurlOption(CURLOPTTYPE_LONG + 157)
    final val HTTP_CONTENT_DECODING = new CurlOption(CURLOPTTYPE_LONG + 158)

    /* Permission used when creating new files and directories on the remote
       server for protocols that support it, SFTP/SCP/FILE */
    final val NEW_FILE_PERMS = new CurlOption(CURLOPTTYPE_LONG + 159)
    final val NEW_DIRECTORY_PERMS = new CurlOption(CURLOPTTYPE_LONG + 160)

    /* Set the behavior of POST when redirecting. Values must be set to one
       of CURL_REDIR* defines below. This used to be called CURLOPT_POST301 */
    final val POSTREDIR = new CurlOption(CURLOPTTYPE_VALUES + 161)

    /* used by scp/sftp to verify the host's public key */
    final val SSH_HOST_PUBLIC_KEY_MD5 = new CurlOption(CURLOPTTYPE_STRINGPOINT + 162)

    /* Callback function for opening socket (instead of socket(2)). Optionally,
       callback is able change the address or refuse to connect returning
       CURL_SOCKET_BAD.  The callback should have type
       curl_opensocket_callback */
    final val OPENSOCKETFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 163)
    final val OPENSOCKETDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 164)

    /* POST volatile input fields. */
    final val COPYPOSTFIELDS = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 165)

    /* set transfer mode (;type=<a|i>) when doing FTP via an HTTP proxy */
    final val PROXY_TRANSFER_MODE = new CurlOption(CURLOPTTYPE_LONG + 166)

    /* Callback function for seeking in the input stream */
    final val SEEKFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 167)
    final val SEEKDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 168)

    /* CRL file */
    final val CRLFILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 169)

    /* Issuer certificate */
    final val ISSUERCERT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 170)

    /* (IPv6) Address scope */
    final val ADDRESS_SCOPE = new CurlOption(CURLOPTTYPE_LONG + 171)

    /* Collect certificate chain info and allow it to get retrievable with
       CURLINFO_CERTINFO after the transfer is complete. */
    final val CERTINFO = new CurlOption(CURLOPTTYPE_LONG + 172)

    /* "name" and "pwd" to use when fetching. */
    final val USERNAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 173)
    final val PASSWORD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 174)

    /* "name" and "pwd" to use with Proxy when fetching. */
    final val PROXYUSERNAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 175)
    final val PROXYPASSWORD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 176)

    /* Comma separated list of hostnames defining no-proxy zones. These should
       match both hostnames directly, and hostnames within a domain. For
       example, local.com will match local.com and www.local.com, but NOT
       notlocal.com or www.notlocal.com. For compatibility with other
       implementations of this, .local.com will be considered to be the same as
       local.com. A single * is the only valid wildcard, and effectively
       disables the use of proxy. */
    final val NOPROXY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 177)

    /* block size for TFTP transfers */
    final val TFTP_BLKSIZE = new CurlOption(CURLOPTTYPE_LONG + 178)

    /* Socks Service */
    /* DEPRECATED, do not use! */
    final val SOCKS5_GSSAPI_SERVICE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 179)

    /* Socks Service */
    final val SOCKS5_GSSAPI_NEC = new CurlOption(CURLOPTTYPE_LONG + 180)

    /* set the bitmask for the protocols that are allowed to be used for the
       transfer, which thus helps the app which takes URLs from users or other
       external inputs and want to restrict what protocol(s) to deal
       with. Defaults to CURLPROTO_ALL. */
    final val PROTOCOLS = new CurlOption(CURLOPTTYPE_LONG + 181)

    /* set the bitmask for the protocols that libcurl is allowed to follow to,
       as a subset of the CURLOPT_PROTOCOLS ones. That means the protocol needs
       to be set in both bitmasks to be allowed to get redirected to. */
    final val REDIR_PROTOCOLS = new CurlOption(CURLOPTTYPE_LONG + 182)

    /* set the SSH knownhost file name to use */
    final val SSH_KNOWNHOSTS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 183)

    /* set the SSH host key callback, must point to a curl_sshkeycallback
       function */
    final val SSH_KEYFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 184)

    /* set the SSH host key callback custom pointer */
    final val SSH_KEYDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 185)

    /* set the SMTP mail originator */
    final val MAIL_FROM = new CurlOption(CURLOPTTYPE_STRINGPOINT + 186)

    /* set the list of SMTP mail receiver(s) */
    final val MAIL_RCPT = new CurlOption(CURLOPTTYPE_SLISTPOINT + 187)

    /* FTP: send PRET before PASV */
    final val FTP_USE_PRET = new CurlOption(CURLOPTTYPE_LONG + 188)

    /* RTSP request method (OPTIONS, SETUP, PLAY, etc...) */
    final val RTSP_REQUEST = new CurlOption(CURLOPTTYPE_VALUES + 189)

    /* The RTSP session identifier */
    final val RTSP_SESSION_ID = new CurlOption(CURLOPTTYPE_STRINGPOINT + 190)

    /* The RTSP stream URI */
    final val RTSP_STREAM_URI = new CurlOption(CURLOPTTYPE_STRINGPOINT + 191)

    /* The Transport: header to use in RTSP requests */
    final val RTSP_TRANSPORT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 192)

    /* Manually initialize the client RTSP CSeq for this handle */
    final val RTSP_CLIENT_CSEQ = new CurlOption(CURLOPTTYPE_LONG + 193)

    /* Manually initialize the server RTSP CSeq for this handle */
    final val RTSP_SERVER_CSEQ = new CurlOption(CURLOPTTYPE_LONG + 194)

    /* The stream to pass to INTERLEAVEFUNCTION. */
    final val INTERLEAVEDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 195)

    /* Let the application define a custom write method for RTP data */
    final val INTERLEAVEFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 196)

    /* Turn on wildcard matching */
    final val WILDCARDMATCH = new CurlOption(CURLOPTTYPE_LONG + 197)

    /* Directory matching callback called before downloading of an
       individual file (chunk) started */
    final val CHUNK_BGN_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 198)

    /* Directory matching callback called after the file (chunk)
       was downloaded, or skipped */
    final val CHUNK_END_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 199)

    /* Change match (fnmatch-like) callback for wildcard matching */
    final val FNMATCH_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 200)

    /* Let the application define custom chunk data pointer */
    final val CHUNK_DATA = new CurlOption(CURLOPTTYPE_CBPOINT + 201)

    /* FNMATCH_FUNCTION user pointer */
    final val FNMATCH_DATA = new CurlOption(CURLOPTTYPE_CBPOINT + 202)

    /* send linked-list of name:port:address sets */
    final val RESOLVE = new CurlOption(CURLOPTTYPE_SLISTPOINT + 203)

    /* Set a username for authenticated TLS */
    final val TLSAUTH_USERNAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 204)

    /* Set a password for authenticated TLS */
    final val TLSAUTH_PASSWORD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 205)

    /* Set authentication type for authenticated TLS */
    final val TLSAUTH_TYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 206)

    /* Set to 1 to enable the "TE:" header in HTTP requests to ask for
       compressed transfer-encoded responses. Set to 0 to disable the use of TE:
       in outgoing requests. The current default is 0, but it might change in a
       future libcurl release.

       libcurl will ask for the compressed methods it knows of, and if that
       isn't any, it will not ask for transfer-encoding at all even if this
       option is set to 1.

     */
    final val TRANSFER_ENCODING = new CurlOption(CURLOPTTYPE_LONG + 207)

    /* Callback function for closing socket (instead of close(2)). The callback
       should have type curl_closesocket_callback */
    final val CLOSESOCKETFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 208)
    final val CLOSESOCKETDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 209)

    /* allow GSSAPI credential delegation */
    final val GSSAPI_DELEGATION = new CurlOption(CURLOPTTYPE_VALUES + 210)

    /* Set the name servers to use for DNS resolution */
    final val DNS_SERVERS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 211)

    /* Time-out accept operations (currently for FTP only) after this amount
       of milliseconds. */
    final val ACCEPTTIMEOUT_MS = new CurlOption(CURLOPTTYPE_LONG + 212)

    /* Set TCP keepalive */
    final val TCP_KEEPALIVE = new CurlOption(CURLOPTTYPE_LONG + 213)

    /* non-universal keepalive knobs (Linux, AIX, HP-UX, more) */
    final val TCP_KEEPIDLE = new CurlOption(CURLOPTTYPE_LONG + 214)
    final val TCP_KEEPINTVL = new CurlOption(CURLOPTTYPE_LONG + 215)

    /* Enable/disable specific SSL features with a bitmask, see CURLSSLOPT_* */
    final val SSL_OPTIONS = new CurlOption(CURLOPTTYPE_VALUES + 216)

    /* Set the SMTP auth originator */
    final val MAIL_AUTH = new CurlOption(CURLOPTTYPE_STRINGPOINT + 217)

    /* Enable/disable SASL initial response */
    final val SASL_IR = new CurlOption(CURLOPTTYPE_LONG + 218)

    /* Function that will be called instead of the internal progress display
     * function. This function should be defined as the curl_xferinfo_callback
     * prototype defines. (Deprecates CURLOPT_PROGRESSFUNCTION) */
    final val XFERINFOFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 219)

    /* The XOAUTH2 bearer token */
    final val XOAUTH2_BEARER = new CurlOption(CURLOPTTYPE_STRINGPOINT + 220)

    /* Set the interface string to use as outgoing network
     * interface for DNS requests.
     * Only supported by the c-ares DNS backend */
    final val DNS_INTERFACE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 221)

    /* Set the local IPv4 address to use for outgoing DNS requests.
     * Only supported by the c-ares DNS backend */
    final val DNS_LOCAL_IP4 = new CurlOption(CURLOPTTYPE_STRINGPOINT + 222)

    /* Set the local IPv6 address to use for outgoing DNS requests.
     * Only supported by the c-ares DNS backend */
    final val DNS_LOCAL_IP6 = new CurlOption(CURLOPTTYPE_STRINGPOINT + 223)

    /* Set authentication options directly */
    final val LOGIN_OPTIONS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 224)

    /* Enable/disable TLS NPN extension (http2 over ssl might fail without) */
    final val SSL_ENABLE_NPN = new CurlOption(CURLOPTTYPE_LONG + 225)

    /* Enable/disable TLS ALPN extension (http2 over ssl might fail without) */
    final val SSL_ENABLE_ALPN = new CurlOption(CURLOPTTYPE_LONG + 226)

    /* Time to wait for a response to a HTTP request containing an
     * Expect: 100-continue header before sending the data anyway. */
    final val EXPECT_100_TIMEOUT_MS = new CurlOption(CURLOPTTYPE_LONG + 227)

    /* This points to a linked list of headers used for proxy requests only,
       struct curl_slist kind */
    final val PROXYHEADER = new CurlOption(CURLOPTTYPE_SLISTPOINT + 228)

    /* Pass in a bitmask of "header options" */
    final val HEADEROPT = new CurlOption(CURLOPTTYPE_VALUES + 229)

    /* The public key in DER form used to validate the peer public key
       this option is used only if SSL_VERIFYPEER is true */
    final val PINNEDPUBLICKEY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 230)

    /* Path to Unix domain socket */
    final val UNIX_SOCKET_PATH = new CurlOption(CURLOPTTYPE_STRINGPOINT + 231)

    /* Set if we should verify the certificate status. */
    final val SSL_VERIFYSTATUS = new CurlOption(CURLOPTTYPE_LONG + 232)

    /* Set if we should enable TLS false start. */
    final val SSL_FALSESTART = new CurlOption(CURLOPTTYPE_LONG + 233)

    /* Do not squash dot-dot sequences */
    final val PATH_AS_IS = new CurlOption(CURLOPTTYPE_LONG + 234)

    /* Proxy Service Name */
    final val PROXY_SERVICE_NAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 235)

    /* Service Name */
    final val SERVICE_NAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 236)

    /* Wait/don't wait for pipe/mutex to clarify */
    final val PIPEWAIT = new CurlOption(CURLOPTTYPE_LONG + 237)

    /* Set the protocol used when curl is given a URL without a protocol */
    final val DEFAULT_PROTOCOL = new CurlOption(CURLOPTTYPE_STRINGPOINT + 238)

    /* Set stream weight, 1 - 256 (default is 16) */
    final val STREAM_WEIGHT = new CurlOption(CURLOPTTYPE_LONG + 239)

    /* Set stream dependency on another CURL handle */
    final val STREAM_DEPENDS = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 240)

    /* Set E-xclusive stream dependency on another CURL handle */
    final val STREAM_DEPENDS_E = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 241)

    /* Do not send any tftp option requests to the server */
    final val TFTP_NO_OPTIONS = new CurlOption(CURLOPTTYPE_LONG + 242)

    /* Linked-list of host:port:connect-to-host:connect-to-port,
       overrides the URL's host:port (only for the network layer) */
    final val CONNECT_TO = new CurlOption(CURLOPTTYPE_SLISTPOINT + 243)

    /* Set TCP Fast Open */
    final val TCP_FASTOPEN = new CurlOption(CURLOPTTYPE_LONG + 244)

    /* Continue to send data if the server responds early with an
     * HTTP status code >= 300 */
    final val KEEP_SENDING_ON_ERROR = new CurlOption(CURLOPTTYPE_LONG + 245)

    /* The CApath or CAfile used to validate the proxy certificate
       this option is used only if PROXY_SSL_VERIFYPEER is true */
    final val PROXY_CAINFO = new CurlOption(CURLOPTTYPE_STRINGPOINT + 246)

    /* The CApath directory used to validate the proxy certificate
       this option is used only if PROXY_SSL_VERIFYPEER is true */
    final val PROXY_CAPATH = new CurlOption(CURLOPTTYPE_STRINGPOINT + 247)

    /* Set if we should verify the proxy in ssl handshake,
       set 1 to verify. */
    final val PROXY_SSL_VERIFYPEER = new CurlOption(CURLOPTTYPE_LONG + 248)

    /* Set if we should verify the Common name from the proxy certificate in ssl
     * handshake, set 1 to check existence, 2 to ensure that it matches
     * the provided hostname. */
    final val PROXY_SSL_VERIFYHOST = new CurlOption(CURLOPTTYPE_LONG + 249)

    /* What version to specifically try to use for proxy.
       See CURL_SSLVERSION defines below. */
    final val PROXY_SSLVERSION = new CurlOption(CURLOPTTYPE_VALUES + 250)

    /* Set a username for authenticated TLS for proxy */
    final val PROXY_TLSAUTH_USERNAME = new CurlOption(CURLOPTTYPE_STRINGPOINT + 251)

    /* Set a password for authenticated TLS for proxy */
    final val PROXY_TLSAUTH_PASSWORD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 252)

    /* Set authentication type for authenticated TLS for proxy */
    final val PROXY_TLSAUTH_TYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 253)

    /* name of the file keeping your private SSL-certificate for proxy */
    final val PROXY_SSLCERT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 254)

    /* type of the file keeping your SSL-certificate ("DER", "PEM", "ENG") for
       proxy */
    final val PROXY_SSLCERTTYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 255)

    /* name of the file keeping your private SSL-key for proxy */
    final val PROXY_SSLKEY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 256)

    /* type of the file keeping your private SSL-key ("DER", "PEM", "ENG") for
       proxy */
    final val PROXY_SSLKEYTYPE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 257)

    /* password for the SSL private key for proxy */
    final val PROXY_KEYPASSWD = new CurlOption(CURLOPTTYPE_STRINGPOINT + 258)

    /* Specify which SSL ciphers to use for proxy */
    final val PROXY_SSL_CIPHER_LIST = new CurlOption(CURLOPTTYPE_STRINGPOINT + 259)

    /* CRL file for proxy */
    final val PROXY_CRLFILE = new CurlOption(CURLOPTTYPE_STRINGPOINT + 260)

    /* Enable/disable specific SSL features with a bitmask for proxy, see
       CURLSSLOPT_* */
    final val PROXY_SSL_OPTIONS = new CurlOption(CURLOPTTYPE_LONG + 261)

    /* Name of pre proxy to use. */
    final val PRE_PROXY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 262)

    /* The public key in DER form used to validate the proxy public key
       this option is used only if PROXY_SSL_VERIFYPEER is true */
    final val PROXY_PINNEDPUBLICKEY = new CurlOption(CURLOPTTYPE_STRINGPOINT + 263)

    /* Path to an abstract Unix domain socket */
    final val ABSTRACT_UNIX_SOCKET = new CurlOption(CURLOPTTYPE_STRINGPOINT + 264)

    /* Suppress proxy CONNECT response headers from user callbacks */
    final val SUPPRESS_CONNECT_HEADERS = new CurlOption(CURLOPTTYPE_LONG + 265)

    /* The request target, instead of extracted from the URL */
    final val REQUEST_TARGET = new CurlOption(CURLOPTTYPE_STRINGPOINT + 266)

    /* bitmask of allowed auth methods for connections to SOCKS5 proxies */
    final val SOCKS5_AUTH = new CurlOption(CURLOPTTYPE_LONG + 267)

    /* Enable/disable SSH compression */
    final val SSH_COMPRESSION = new CurlOption(CURLOPTTYPE_LONG + 268)

    /* Post MIME data. */
    final val MIMEPOST = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 269)

    /* Time to use with the CURLOPT_TIMECONDITION. Specified in number of
       seconds since 1 Jan 1970. */
    final val TIMEVALUE_LARGE = new CurlOption(CURLOPTTYPE_OFF_T + 270)

    /* Head start in milliseconds to give happy eyeballs. */
    final val HAPPY_EYEBALLS_TIMEOUT_MS = new CurlOption(CURLOPTTYPE_LONG + 271)

    /* Function that will be called before a resolver request is made */
    final val RESOLVER_START_FUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 272)

    /* User data to pass to the resolver start callback. */
    final val RESOLVER_START_DATA = new CurlOption(CURLOPTTYPE_CBPOINT + 273)

    /* send HAProxy PROXY protocol header? */
    final val HAPROXYPROTOCOL = new CurlOption(CURLOPTTYPE_LONG + 274)

    /* shuffle addresses before use when DNS returns multiple */
    final val DNS_SHUFFLE_ADDRESSES = new CurlOption(CURLOPTTYPE_LONG + 275)

    /* Specify which TLS 1.3 ciphers suites to use */
    final val TLS13_CIPHERS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 276)
    final val PROXY_TLS13_CIPHERS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 277)

    /* Disallow specifying username/login in URL. */
    final val DISALLOW_USERNAME_IN_URL = new CurlOption(CURLOPTTYPE_LONG + 278)

    /* DNS-over-HTTPS URL */
    final val DOH_URL = new CurlOption(CURLOPTTYPE_STRINGPOINT + 279)

    /* Preferred buffer size to use for uploads */
    final val UPLOAD_BUFFERSIZE = new CurlOption(CURLOPTTYPE_LONG + 280)

    /* Time in ms between connection upkeep calls for long-lived connections. */
    final val UPKEEP_INTERVAL_MS = new CurlOption(CURLOPTTYPE_LONG + 281)

    /* Specify URL using CURL URL API. */
    final val CURLU = new CurlOption(CURLOPTTYPE_OBJECTPOINT + 282)

    /* add trailing data just after no more data is available */
    final val TRAILERFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 283)

    /* pointer to be passed to HTTP_TRAILER_FUNCTION */
    final val TRAILERDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 284)

    /* set this to 1L to allow HTTP/0.9 responses or 0L to disallow */
    final val HTTP09_ALLOWED = new CurlOption(CURLOPTTYPE_LONG + 285)

    /* alt-svc control bitmask */
    final val ALTSVC_CTRL = new CurlOption(CURLOPTTYPE_LONG + 286)

    /* alt-svc cache file name to possibly read from/write to */
    final val ALTSVC = new CurlOption(CURLOPTTYPE_STRINGPOINT + 287)

    /* maximum age (idle time) of a connection to consider it for reuse
     * (in seconds) */
    final val MAXAGE_CONN = new CurlOption(CURLOPTTYPE_LONG + 288)

    /* SASL authorisation identity */
    final val SASL_AUTHZID = new CurlOption(CURLOPTTYPE_STRINGPOINT + 289)

    /* allow RCPT TO command to fail for some recipients */
    final val MAIL_RCPT_ALLLOWFAILS = new CurlOption(CURLOPTTYPE_LONG + 290)

    /* the private SSL-certificate as a "blob" */
    final val SSLCERT_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 291)
    final val SSLKEY_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 292)
    final val PROXY_SSLCERT_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 293)
    final val PROXY_SSLKEY_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 294)
    final val ISSUERCERT_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 295)

    /* Issuer certificate for proxy */
    final val PROXY_ISSUERCERT = new CurlOption(CURLOPTTYPE_STRINGPOINT + 296)
    final val PROXY_ISSUERCERT_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 297)

    /* the EC curves requested by the TLS client (RFC 8422, 5.1);
     * OpenSSL support via 'set_groups'/'set_curves':
     * https://www.openssl.org/docs/manmaster/man3/SSL_CTX_set1_groups.html
     */
    final val SSL_EC_CURVES = new CurlOption(CURLOPTTYPE_STRINGPOINT + 298)

    /* HSTS bitmask */
    final val HSTS_CTRL = new CurlOption(CURLOPTTYPE_LONG + 299)
    /* HSTS file name */
    final val HSTS = new CurlOption(CURLOPTTYPE_STRINGPOINT + 300)

    /* HSTS read callback */
    final val HSTSREADFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 301)
    final val HSTSREADDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 302)

    /* HSTS write callback */
    final val HSTSWRITEFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 303)
    final val HSTSWRITEDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 304)

    /* Parameters for V4 signature */
    final val AWS_SIGV4 = new CurlOption(CURLOPTTYPE_STRINGPOINT + 305)

    /* Same as CURLOPT_SSL_VERIFYPEER but for DoH (DNS-over-HTTPS) servers. */
    final val DOH_SSL_VERIFYPEER = new CurlOption(CURLOPTTYPE_LONG + 306)

    /* Same as CURLOPT_SSL_VERIFYHOST but for DoH (DNS-over-HTTPS) servers. */
    final val DOH_SSL_VERIFYHOST = new CurlOption(CURLOPTTYPE_LONG + 307)

    /* Same as CURLOPT_SSL_VERIFYSTATUS but for DoH (DNS-over-HTTPS) servers. */
    final val DOH_SSL_VERIFYSTATUS = new CurlOption(CURLOPTTYPE_LONG + 308)

    /* The CA certificates as "blob" used to validate the peer certificate
       this option is used only if SSL_VERIFYPEER is true */
    final val CAINFO_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 309)

    /* The CA certificates as "blob" used to validate the proxy certificate
       this option is used only if PROXY_SSL_VERIFYPEER is true */
    final val PROXY_CAINFO_BLOB = new CurlOption(CURLOPTTYPE_BLOB + 310)

    /* used by scp/sftp to verify the host's public key */
    final val SSH_HOST_PUBLIC_KEY_SHA256 = new CurlOption(CURLOPTTYPE_STRINGPOINT + 311)

    /* Function that will be called immediately before the initial request
       is made on a connection (after any protocol negotiation step).  */
    final val PREREQFUNCTION = new CurlOption(CURLOPTTYPE_FUNCTIONPOINT + 312)

    /* Data passed to the CURLOPT_PREREQFUNCTION callback */
    final val PREREQDATA = new CurlOption(CURLOPTTYPE_CBPOINT + 313)

    /* maximum age (since creation) of a connection to consider it for reuse
     * (in seconds) */
    final val MAXLIFETIME_CONN = new CurlOption(CURLOPTTYPE_LONG + 314)

    /* Set MIME option flags. */
    final val MIME_OPTIONS = new CurlOption(CURLOPTTYPE_LONG + 315)

  type WriteCallback = Array[Byte] => Unit

  private var writeCallbackSerial = 1L
  private val writeCallbacks = new mutable.LongMap[WriteCallback]
  private val writeCallback: lib.curl_write_callback =
    (ptr: Ptr[CChar], size: CSize, nmemb: CSize, userdata: Ptr[Byte]) =>
      val len = nmemb.toInt
      val data = new Array[Byte](len)
      var i = 0

      while i < len do
        data(i) = !(ptr + i.toULong)
        i += 1

      writeCallbacks get userdata.toLong foreach { cb =>
        cb(data)
        writeCallbacks -= userdata.toLong
      }
      nmemb

  implicit class Curl(val curl: lib.CURL) extends AnyVal:
    def isNull: Boolean = curl == null

    def nonNull: Boolean = curl != null

    def easySetopt(option: CurlOption, arg: String): Code =
      Zone(implicit z => lib.curl_easy_setopt(curl, option.value, toCString(arg)))

    def easySetopt(option: CurlOption, arg: Long): Code =
      lib.curl_easy_setopt(curl, option.value, arg.asInstanceOf[CLong])

    def easySetoptWriteFunction(cb: WriteCallback): Code =
      lib.curl_easy_setopt(curl, CurlOption.WRITEDATA.value, writeCallbackSerial)
      writeCallbacks(writeCallbackSerial) = cb
      writeCallbackSerial += 1
      lib.curl_easy_setopt(curl, CurlOption.WRITEFUNCTION.value, writeCallback)

    def easyPerform: Code = lib.curl_easy_perform(curl)

    def easyCleanup(): Unit = lib.curl_easy_cleanup(curl)

    def easyGetinfo(info: Info): (Code, Long) =
      val longp = stackalloc[CLong]()

      (lib.curl_easy_getinfo(curl, info.value, longp), !longp)
  end Curl

  def multiInit: Curlm = lib.curl_multi_init

  implicit class Curlm(val multi: lib.CURLM) extends AnyVal:
    def isNull: Boolean = multi == null

    def nonNull: Boolean = multi != null

    def multiSocketAction(sockfd: Int, ev_bitmask: Int): (Code, Int) = //
      val handles = stackalloc[CInt]()

      (lib.curl_multi_socket_action(multi, sockfd, ev_bitmask, handles), !handles)
  end Curlm

  def easyInit: Curl = lib.curl_easy_init

  def globalCleanup(): Unit = lib.curl_global_cleanup()

  def easyStrerror(code: Code): String = fromCString(lib.curl_easy_strerror(code.value))

  val CURLINFO_STRING = 0x100000
  val CURLINFO_LONG = 0x200000
  val CURLINFO_DOUBLE = 0x300000
  val CURLINFO_SLIST = 0x400000
  val CURLINFO_PTR = 0x400000 /* same as SLIST */
  val CURLINFO_SOCKET = 0x500000
  val CURLINFO_OFF_T = 0x600000

  implicit class Info(val value: lib.CURLINFO) extends AnyVal

  object Info:
    final val EFFECTIVE_URL = new Info(CURLINFO_STRING + 1)
    final val RESPONSE_CODE = new Info(CURLINFO_LONG + 2)
    final val TOTAL_TIME = new Info(CURLINFO_DOUBLE + 3)
    final val NAMELOOKUP_TIME = new Info(CURLINFO_DOUBLE + 4)
    final val CONNECT_TIME = new Info(CURLINFO_DOUBLE + 5)
    final val PRETRANSFER_TIME = new Info(CURLINFO_DOUBLE + 6)
    final val SIZE_UPLOAD = new Info(CURLINFO_DOUBLE + 7)
    final val SIZE_UPLOAD_T = new Info(CURLINFO_OFF_T + 7)
    final val SIZE_DOWNLOAD = new Info(CURLINFO_DOUBLE + 8)
    final val SIZE_DOWNLOAD_T = new Info(CURLINFO_OFF_T + 8)
    final val SPEED_DOWNLOAD = new Info(CURLINFO_DOUBLE + 9)
    final val SPEED_DOWNLOAD_T = new Info(CURLINFO_OFF_T + 9)
    final val SPEED_UPLOAD = new Info(CURLINFO_DOUBLE + 10)
    final val SPEED_UPLOAD_T = new Info(CURLINFO_OFF_T + 10)
    final val HEADER_SIZE = new Info(CURLINFO_LONG + 11)
    final val REQUEST_SIZE = new Info(CURLINFO_LONG + 12)
    final val SSL_VERIFYRESULT = new Info(CURLINFO_LONG + 13)
    final val FILETIME = new Info(CURLINFO_LONG + 14)
    final val FILETIME_T = new Info(CURLINFO_OFF_T + 14)
    final val CONTENT_LENGTH_DOWNLOAD = new Info(CURLINFO_DOUBLE + 15)
    final val CONTENT_LENGTH_DOWNLOAD_T = new Info(CURLINFO_OFF_T + 15)
    final val CONTENT_LENGTH_UPLOAD = new Info(CURLINFO_DOUBLE + 16)
    final val CONTENT_LENGTH_UPLOAD_T = new Info(CURLINFO_OFF_T + 16)
    final val STARTTRANSFER_TIME = new Info(CURLINFO_DOUBLE + 17)
    final val CONTENT_TYPE = new Info(CURLINFO_STRING + 18)
    final val REDIRECT_TIME = new Info(CURLINFO_DOUBLE + 19)
    final val REDIRECT_COUNT = new Info(CURLINFO_LONG + 20)
    final val PRIVATE = new Info(CURLINFO_STRING + 21)
    final val HTTP_CONNECTCODE = new Info(CURLINFO_LONG + 22)
    final val HTTPAUTH_AVAIL = new Info(CURLINFO_LONG + 23)
    final val PROXYAUTH_AVAIL = new Info(CURLINFO_LONG + 24)
    final val OS_ERRNO = new Info(CURLINFO_LONG + 25)
    final val NUM_CONNECTS = new Info(CURLINFO_LONG + 26)
    final val SSL_ENGINES = new Info(CURLINFO_SLIST + 27)
    final val COOKIELIST = new Info(CURLINFO_SLIST + 28)
    final val LASTSOCKET = new Info(CURLINFO_LONG + 29)
    final val FTP_ENTRY_PATH = new Info(CURLINFO_STRING + 30)
    final val REDIRECT_URL = new Info(CURLINFO_STRING + 31)
    final val PRIMARY_IP = new Info(CURLINFO_STRING + 32)
    final val APPCONNECT_TIME = new Info(CURLINFO_DOUBLE + 33)
    final val CERTINFO = new Info(CURLINFO_PTR + 34)
    final val CONDITION_UNMET = new Info(CURLINFO_LONG + 35)
    final val RTSP_SESSION_ID = new Info(CURLINFO_STRING + 36)
    final val RTSP_CLIENT_CSEQ = new Info(CURLINFO_LONG + 37)
    final val RTSP_SERVER_CSEQ = new Info(CURLINFO_LONG + 38)
    final val RTSP_CSEQ_RECV = new Info(CURLINFO_LONG + 39)
    final val PRIMARY_PORT = new Info(CURLINFO_LONG + 40)
    final val LOCAL_IP = new Info(CURLINFO_STRING + 41)
    final val LOCAL_PORT = new Info(CURLINFO_LONG + 42)
    final val TLS_SESSION = new Info(CURLINFO_PTR + 43)
    final val ACTIVESOCKET = new Info(CURLINFO_SOCKET + 44)
    final val TLS_SSL_PTR = new Info(CURLINFO_PTR + 45)
    final val HTTP_VERSION = new Info(CURLINFO_LONG + 46)
    final val PROXY_SSL_VERIFYRESULT = new Info(CURLINFO_LONG + 47)
    final val PROTOCOL = new Info(CURLINFO_LONG + 48)
    final val SCHEME = new Info(CURLINFO_STRING + 49)
    final val TOTAL_TIME_T = new Info(CURLINFO_OFF_T + 50)
    final val NAMELOOKUP_TIME_T = new Info(CURLINFO_OFF_T + 51)
    final val CONNECT_TIME_T = new Info(CURLINFO_OFF_T + 52)
    final val PRETRANSFER_TIME_T = new Info(CURLINFO_OFF_T + 53)
    final val STARTTRANSFER_TIME_T = new Info(CURLINFO_OFF_T + 54)
    final val REDIRECT_TIME_T = new Info(CURLINFO_OFF_T + 55)
    final val APPCONNECT_TIME_T = new Info(CURLINFO_OFF_T + 56)
    final val RETRY_AFTER = new Info(CURLINFO_OFF_T + 57)
    final val EFFECTIVE_METHOD = new Info(CURLINFO_STRING + 58)
    final val PROXY_ERROR = new Info(CURLINFO_LONG + 59)
    final val REFERER = new Info(CURLINFO_STRING + 60)
