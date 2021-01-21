package com.zarinpal.auth.view.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chaos.view.PinView
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.controller.IssueTokenHttpClient
import com.zarinpal.auth.midleware.IssueTokenMiddleware
import com.zarinpal.auth.tools.newScope
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
import java.util.concurrent.TimeUnit

internal class OtpFragment : Fragment(R.layout.otp_auth_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewPumper.pump(view, Font.Light)

        val username = arguments?.getString("username")
        val params = JSONObject(arguments?.getString("params") ?: "")


        val pinView = view.findViewById<PinView>(R.id.pin_view)
        view.findViewById<AppCompatTextView>(R.id.txt_message).apply {
            text = String.format(text.toString(), username)
            typeface = Font.Bold.toTypeface(requireContext())
        }

        view.findViewById<AppCompatTextView>(R.id.txt_time).apply {
            runCountDownTimer(
                params.optLong("waiting_time", 60) * 1000,
                1000
            ) { duration, isFinish ->
                val remainTime = String.format(
                    "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                )

                this@apply.text = String.format(
                    context.getString(R.string.zarinpal_auth_sdk_resend_code_time),
                    remainTime
                )
            }
        }

        view.findViewById<ButtonProgress>(R.id.btn).apply {
            setOnClickListener {

                val otp = pinView.text.toString();

                if (otp.isEmpty()) {
                    return@setOnClickListener
                }

                progressVisibility = true
                IssueTokenMiddleware(username!!, otp, Holder.authClient!!)
                    .asLiveData()
                    .observe(this@OtpFragment, Observer {
                        progressVisibility = false
                        it.getOrElse {
                            it.toToast(requireContext()).show()
                            Holder.callback?.onException(it)
                            return@Observer
                        }.let {
                            JSONObject(it.body).apply {
                                Holder.callback?.onIssueAccessToken(
                                    getString("token_type"),
                                    getString("access_token"),
                                    getString("refresh_token"),
                                    getLong("expires_in")
                                )
                            }

                            (requireParentFragment() as DialogFragment).dismissAllowingStateLoss()

                        }
                    })


            }
        }
    }

}