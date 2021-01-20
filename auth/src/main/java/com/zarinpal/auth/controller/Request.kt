package com.zarinpal.auth.controller

data class Request(
    val Url: String,
    val method: HttpMethod,
    val params: HashMap<String, Any?>? = null,
    val header: HashMap<String, String>? = hashMapOf(),
    val contentType: ContentType? = null
)

data class Response(
    val body: String,
    val statusCode: Int,
    val header: MutableMap<String, MutableList<String>>,
    val request: Request
)

enum class ContentType(val entry: Pair<String, String>) {
    ApplicationJson(Pair("Content-Type", "application/json")),
    ApplicationFormUrlEncoded(Pair("Content-Type", "application/x-www-form-urlencoded"))
}

enum class HttpMethod(val value: String) {
    Post("POST"),
    Get("GET"),
    Put("PUT"),
    Delete("Delete")
}