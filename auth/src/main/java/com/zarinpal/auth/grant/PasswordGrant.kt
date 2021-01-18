package com.zarinpal.auth.grant

import org.json.JSONObject

internal class PasswordGrant(
    private val grantType: String,
    private val clientSecret: String,
    private val clientId: String,
    private val scope: String
) : IAuthRequestGrant {


    override fun toSerialiaze() = JSONObject().apply {
        put("grant_type", grantType)
        put("client_secret", clientSecret)
        put("client_id", clientId)
        put("scope", scope)
    }.toString()
}