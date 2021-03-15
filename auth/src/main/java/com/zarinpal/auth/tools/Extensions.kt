package com.zarinpal.auth.tools

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.widget.Toast
import androidx.core.os.bundleOf
import com.zarinpal.ZarinPalAuth
import com.zarinpal.auth.Callback
import com.zarinpal.auth.exception.HttpException
import com.zarinpal.builder.ZarinPalAuthPresentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

internal fun runCountDownTimer(
    milliInFuture: Long,
    interval: Long,
    action: (milliFinished: Long, isFinish: Boolean) -> Unit
) {
    var timer: CountDownTimer? = null
    timer = object : CountDownTimer(milliInFuture, interval) {
        override fun onTick(millisUntilFinished: Long) {
            action(millisUntilFinished, false)
        }

        override fun onFinish() {
            action(0, true)
            timer?.cancel()

        }
    }.start()
}

internal fun String.isValidPhoneNumber(): Boolean {
    return this.startsWith("0") && this.length == 11
}


internal fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.trim { it <= ' ' }).matches()
}


internal fun JSONArray.first(): JSONObject = getJSONObject(0)
internal val errorMap =
    mapOf(
        HttpException.USER_NOT_FOUND to "کاربر یافت نشد.",
        HttpException.INVALID_GRANT to "رمز وارد شده اشتباه است."
    )

internal fun Throwable.toToast(
    context: Context,
    action: ((readableCode: String?) -> Unit)? = null
): Toast {
    if (this !is HttpException) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }


    return readableCode.run {
        action?.invoke(this)
        Toast.makeText(context, errorMap[this], Toast.LENGTH_SHORT)
    }
}

internal val HttpException.readableCode: String?
    get() = JSONObject(message)
        .getJSONArray("errors")
        .first()
        .getString("readable_code")

internal fun newScope(context: CoroutineContext = Dispatchers.IO) = CoroutineScope(context)

internal fun <T> mutableSingleLiveEvent(block: SingleLiveEvent<T>.() -> Unit): SingleLiveEvent<T> {
    return SingleLiveEvent<T>().apply { block(this) }
}

suspend fun ZarinPalAuthPresentation.start(): Bundle {
    return suspendCancellableCoroutine {
        start(object : Callback {
            override fun onIssueAccessToken(
                typeToken: String?,
                accessToken: String?,
                refreshToken: String?,
                expireIn: Long
            ) {
                bundleOf(
                    "typeToken" to typeToken,
                    "accessToken" to accessToken,
                    "refreshToken" to refreshToken,
                    "expireIn" to expireIn
                ).apply { it.resumeWith(Result.success(this)) }
            }

            override fun onException(throwable: Throwable?) {
                it.resumeWithException(throwable ?: Exception())
            }

        })
    }
}

