package com.zarinpal.auth.exception

import com.zarinpal.auth.controller.Response
import java.lang.IllegalArgumentException

class HttpException(
    override val message: String,
    val statusCode: Int,
    val response: Response
) : Exception(message) {
    companion object {
        const val USER_NOT_FOUND = "REST_USER_NOT_FOUND"
        const val INVALID_GRANT = "INVALID_GRANT"
    }
}


class IllegalArgumentAuthException(type: String) :
    IllegalArgumentException("$type is Empty, $type must not empty or null.")