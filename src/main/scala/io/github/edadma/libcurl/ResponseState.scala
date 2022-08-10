package io.github.edadma.libcurl

import collection.mutable

case class ResponseState(
    var code: Int = 200,
    var headers: mutable.Map[String, String] = mutable.Map(),
    var body: String = "",
)
