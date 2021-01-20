package com.zarinpal.auth.midleware

import androidx.lifecycle.LiveData
import com.zarinpal.auth.tools.newScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Middleware is a wrapper between Http Client and UI layer.
 */
internal interface Middleware<T> {
    val scope: CoroutineScope get() = newScope(Dispatchers.IO)

    fun asLiveData(): LiveData<Result<T>>
    fun asResult(): Result<T> = Result.failure(Exception("Not implemented this method."))
}