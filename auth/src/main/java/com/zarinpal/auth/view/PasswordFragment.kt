package com.zarinpal.auth.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.chaos.view.PinView
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.controller.IssueTokenHttpClient
import com.zarinpal.auth.tools.runCountDownTimer
import com.zarinpal.auth.tools.toToast
import com.zarinpal.provider.core.ButtonProgress
import com.zarinpal.provider.core.Font
import com.zarinpal.provider.core.ViewPumper
import com.zarinpal.provider.core.toTypeface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

internal class PasswordFragment : Fragment(R.layout.otp_auth_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewPumper.pump(view, Font.Light)
        val scope = CoroutineScope(Dispatchers.IO)
        val username = arguments?.getString("username")
        val params = JSONObject(arguments?.getString("params") ?: "")
        val authClient = arguments?.getString("auth_client")


        val pinView = view.findViewById<PinView>(R.id.pin_view)
        view.findViewById<AppCompatTextView>(R.id.txt_message).apply {
            text = String.format(text.toString(), username)
            typeface = Font.Bold.toTypeface(requireContext())
        }

        view.findViewById<AppCompatTextView>(R.id.txt_time).apply {
            runCountDownTimer(
                params.getLong("waiting_time") * 1000,
                1000
            ) { duration, isFinish ->
                val remainTime = String.format(
                    "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                )

                this@apply.text = String.format(
                    "تا %s ارسال مجدد رمز‌یکبار مصرف به شما امکان پذیر است.",
                    remainTime
                )
            }
        }


        view.findViewById<ButtonProgress>(R.id.btn).apply {
            setOnClickListener {
                if (pinView.text.toString().isEmpty()) {
                    return@setOnClickListener
                }

                progressVisibility = true
                scope.launch {
                    runCatching {
                        IssueTokenHttpClient(
                            username!!,
                            pinView.text.toString(),
                            authClient!!
                        ).fetch()
                    }.fold({
                        progressVisibility = false
                        JSONObject(it.body).apply {
                            withContext(Dispatchers.Main) {
                                Holder.callback?.onIssueAccessToken(
                                    getString("token_type"),
                                    getString("access_token"),
                                    getString("refresh_token"),
                                    getLong("expires_in")
                                )
                            }
                        }

                    }, {
                        withContext(Dispatchers.Main) {
                            progressVisibility = false
                            it.toToast(requireContext()).show()
                            Holder.callback?.onException(it)
                        }
                    })
                }


            }
        }


    }
}


