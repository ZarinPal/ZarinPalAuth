package com.zarinpal.auth.controller

import com.zarinpal.auth.APIs

internal class InitializerHttpClient(username: String) : HttpClient(
    Request(
        APIs.INITIALIZER,
        HttpMethod.Post,
        hashMapOf("username" to username, "channel" to "sms"),
        contentType = ContentType.ApplicationJson
    )
)