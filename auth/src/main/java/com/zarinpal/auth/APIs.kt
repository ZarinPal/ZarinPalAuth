package com.zarinpal.auth

internal class APIs {
    companion object {
        private const val END_POINT = "https://next.zarinpal.com"
        const val INITIALIZER = "$END_POINT/api/oauth/initialize"
        const val ISSUE_TOKEN = "$END_POINT/api/oauth/token"
    }
}