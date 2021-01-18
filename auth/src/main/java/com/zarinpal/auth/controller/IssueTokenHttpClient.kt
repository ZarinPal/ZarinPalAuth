package com.zarinpal.auth.controller

import com.zarinpal.auth.APIs
import org.json.JSONObject

internal class IssueTokenHttpClient(username: String, password: String, params: String) :
    HttpClient(
        Request(
            APIs.ISSUE_TOKEN,
            HttpMethod.Post,
            hashMapOf<String, Any?>("username" to username, "password" to password).apply {
                JSONObject(params).let { json ->
                    json.keys().forEach { this[it] = json.getString(it) }
                }
            },
            contentType = ContentType.ApplicationFormUrlEncoded
        )
    )