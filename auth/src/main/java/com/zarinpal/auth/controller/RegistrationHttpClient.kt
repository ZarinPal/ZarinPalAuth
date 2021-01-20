package com.zarinpal.auth.controller

import com.zarinpal.auth.APIs

internal class RegistrationHttpClient(
    name: String,
    family: String,
    mobile: String
) : HttpClient(
    Request(
        APIs.REGISTER,
        HttpMethod.Post,
        hashMapOf("first_name" to name, "last_name" to family, "cell_number" to mobile),
        contentType = ContentType.ApplicationJson
    )
)

