package com.zarinpal.auth.view.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.exception.HttpException
import com.zarinpal.auth.midleware.InitializerMiddleware
import com.zarinpal.auth.tools.isValidEmail
import com.zarinpal.auth.tools.isValidPhoneNumber
import com.zarinpal.auth.tools.toToast
import com.zarinpal.auth.view.register.RegistrationFragment
import com.zarinpal.provider.core.ButtonProgress
import com.zarinpal.provider.core.Font
import com.zarinpal.provider.core.ViewPumper

internal class InitializerFragment : Fragment(R.layout.init_auth_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewPumper.pump(view, Font.Light)
        super.onViewCreated(view, savedInstanceState)

        val edtUsername = view.findViewById<EditText>(R.id.edt_username)
        val btn = view.findViewById<ButtonProgress>(R.id.btn)



        btn.setOnClickListener {
            edtUsername.text.toString().apply {
                if (this.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.zarinpal_auth_sdk_enter_email_or_phone_number),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }



                if (this.matches("\\d+".toRegex()) && !this.isValidPhoneNumber()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.zarinpal_auth_sdk_wrong_entered_phone_number),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (!this.matches("\\d+".toRegex()) && !this.isValidEmail()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.zarinpal_auth_sdk_wrong_entered_email),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


                btn.progressVisibility = true
                InitializerMiddleware(this)
                    .asLiveData()
                    .observe(this@InitializerFragment, Observer {

                        btn.progressVisibility = false
                        val response = it.getOrElse {
                            it.toToast(requireContext()).show()
                            Holder.callback?.onException(it)
                            return@Observer
                        }

                        requireFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, OtpFragment()
                                .apply {
                                    arguments = bundleOf(
                                        "username" to edtUsername.text.toString(),
                                        "params" to response.body
                                    )
                                }).commitAllowingStateLoss()

                    })

            }
        }
    }

}