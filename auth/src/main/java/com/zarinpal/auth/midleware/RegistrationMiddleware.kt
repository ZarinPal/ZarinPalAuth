package com.zarinpal.auth.midleware

import androidx.lifecycle.LiveData
import com.zarinpal.auth.controller.InitializerHttpClient
import com.zarinpal.auth.controller.RegistrationHttpClient
import com.zarinpal.auth.controller.Response
import com.zarinpal.auth.tools.mutableSingleLiveEvent
import kotlinx.coroutines.launch


@Deprecated("Registration is automated.")
class RegistrationMiddleware(
    private val name: String,
    private val family: String,
    private val mobile: String
) : Middleware<Response> {
    override fun asLiveData(): LiveData<Result<Response>> {
        return mutableSingleLiveEvent {
            scope.launch {
                runCatching {
                    if (RegistrationHttpClient(name, family, mobile).fetch().statusCode != 200) {
                        return@runCatching Result.failure<Throwable>(Exception())
                    }

                    InitializerHttpClient(mobile).fetch().apply {
                        postValue(Result.success(this))
                    }
                }.getOrElse {
                    Result.failure<Throwable>(it)
                }
            }
        }

    }
}