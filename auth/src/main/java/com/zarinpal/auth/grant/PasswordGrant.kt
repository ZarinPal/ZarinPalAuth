package com.zarinpal.auth.grant

import com.zarinpal.auth.exception.IllegalArgumentAuthException
import org.json.JSONObject

internal class PasswordGrant(
    private val grantType: String,
    private val clientSecret: String,
    private val clientId: String,
    private val scope: String
) : IAuthRequestGrant {


    override fun toSerialiaze() = JSONObject().apply {


        if (grantType.isEmpty()) {
            throw IllegalArgumentAuthException("Grant type")
        }

        if (clientSecret.isEmpty()) {
            throw IllegalArgumentAuthException("Client secret")
        }

        if (clientId.isEmpty()) {
            throw IllegalArgumentAuthException("Client id")
        }

        if (scope.isEmpty()) {
            throw IllegalArgumentAuthException("Scope")
        }

        put("grant_type", grantType)
        put("client_secret", clientSecret)
        put("client_id", clientId)
        put("scope", scope)

    }.toString()
}