package com.zarinpal.auth.midleware

import androidx.lifecycle.LiveData
import com.zarinpal.auth.controller.InitializerHttpClient
import com.zarinpal.auth.controller.Response
import com.zarinpal.auth.tools.mutableSingleLiveEvent
import kotlinx.coroutines.launch


class InitializerMiddleware(private val username: String) : Middleware<Response> {
    override fun asLiveData(): LiveData<Result<Response>> {


        return mutableSingleLiveEvent<Result<Response>> {
            scope.launch {
                InitializerHttpClient(username).runCatching {
                    Result.success(fetch())
                }.getOrElse {
                    Result.failure(it)
                }.let {
                    postValue(it)
                }
            }
        }
    }
}