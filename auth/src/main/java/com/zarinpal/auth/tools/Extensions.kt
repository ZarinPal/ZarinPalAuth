package com.zarinpal.auth.tools

import android.content.Context
import android.os.CountDownTimer
import android.util.Patterns
import android.widget.Toast
import com.zarinpal.auth.exception.HttpException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

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


internal fun JSONArray.first(): JSONObject = this.getJSONObject(0)
internal val errorMap =
    mapOf(
        HttpException.USER_NOT_FOUND to "کاربر یافت نشد.",
        HttpException.INVALID_GRANT to "رمز وارد شده اشتباه است."
    )

internal fun Throwable.toToast(
    context: Context,
    action: ((readableCode: String) -> Unit)? = null
): Toast {
    if (this !is HttpException) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }

    val readable = JSONObject(this.message)
        .getJSONArray("errors")
        .first()
        .getString("readable_code")


    action?.invoke(readable)

    return Toast.makeText(context, errorMap[readable], Toast.LENGTH_SHORT)
}

internal fun newScope(context: CoroutineContext = Dispatchers.IO) = CoroutineScope(context)

internal fun <T> mutableSingleLiveEvent(block: SingleLiveEvent<T>.() -> Unit): SingleLiveEvent<T> {
    return SingleLiveEvent<T>().apply { block(this) }
}

