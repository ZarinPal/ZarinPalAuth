package com.zarinpal.auth.midleware

import androidx.lifecycle.LiveData
import com.zarinpal.auth.Holder
import com.zarinpal.auth.controller.InitializerHttpClient
import com.zarinpal.auth.controller.IssueTokenHttpClient
import com.zarinpal.auth.controller.Response
import com.zarinpal.auth.tools.mutableSingleLiveEvent
import kotlinx.coroutines.launch

class IssueTokenMiddleware(
    private val username: String,
    private val otp: String,
    private val authClient: String
) : Middleware<Response> {
    override fun asLiveData(): LiveData<Result<Response>> {

        return mutableSingleLiveEvent {
            scope.launch {
                IssueTokenHttpClient(
                    username,
                    otp,
                    authClient
                ).runCatching {
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