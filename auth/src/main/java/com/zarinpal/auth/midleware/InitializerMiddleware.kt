package com.zarinpal.auth.midleware

import androidx.lifecycle.LiveData
import com.zarinpal.auth.controller.InitializerHttpClient
import com.zarinpal.auth.controller.RegistrationHttpClient
import com.zarinpal.auth.controller.Response
import com.zarinpal.auth.exception.HttpException
import com.zarinpal.auth.tools.mutableSingleLiveEvent
import com.zarinpal.auth.tools.newScope
import com.zarinpal.auth.tools.readableCode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch


class InitializerMiddleware(private val username: String) : Middleware<Response> {
    override fun asLiveData(): LiveData<Result<Response>> {


        return mutableSingleLiveEvent<Result<Response>> {
            scope.launch {

                val initRequest = async { InitializerHttpClient(username) }
                val registerRequest = async { RegistrationHttpClient(mobile = username) }

                initRequest.await().runCatching {
                    Result.success(fetch())
                }.getOrElse {

                    if ((it as? HttpException)?.readableCode != HttpException.USER_NOT_FOUND) {
                        Result.failure<Throwable>(it)
                        return@launch
                    }
                    registerRequest.await().runCatching {
                        Result.success(fetch())
                    }.getOrElse {
                        Result.failure(it)
                    }
                }.let {
                    postValue(it)
                }
            }
        }
    }
}