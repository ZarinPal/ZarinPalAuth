package com.zarinpal.auth.view

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.controller.InitializerHttpClient
import com.zarinpal.auth.tools.isValidEmail
import com.zarinpal.auth.tools.isValidPhoneNumber
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

class InitializerFragment : Fragment(R.layout.init_auth_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewPumper.pump(view, Font.Light)
        super.onViewCreated(view, savedInstanceState)
        val scope = CoroutineScope(Dispatchers.IO)

        val appName = arguments?.getString("app_name")
        val message = arguments?.getString("message")
        val authClient = arguments?.getString("auth_client")


        val edtUsername = view.findViewById<EditText>(R.id.edt_username)
        val btn = view.findViewById<ButtonProgress>(R.id.btn)

        view.findViewById<AppCompatTextView>(R.id.txt_message).apply {
            typeface = Font.Light.toTypeface(requireContext())
            if (message != null) {
                text = message
                visibility = View.VISIBLE
            }
        }

        view.findViewById<AppCompatTextView>(R.id.txt_welcome).apply {
            typeface = Font.Bold.toTypeface(requireContext())
            text = String.format(text.toString(), appName)
        }


        btn.setOnClickListener {
            edtUsername.text.toString().apply {
                if (this.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "ایمیل یا شماره همراه وارد نمایید.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }



                if (this.matches("\\d+".toRegex()) && !this.isValidPhoneNumber()) {
                    Toast.makeText(
                        requireContext(),
                        "شماره همراه وارد شده اشتباه است.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (!this.matches("\\d+".toRegex()) && !this.isValidEmail()) {
                    Toast.makeText(
                        requireContext(),
                        "ایمیل وارد شده اشتباه است.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


                btn.progressVisibility = true
                scope.launch {
                    runCatching {
                        InitializerHttpClient(this@apply).fetch()
                    }.fold({
                        btn.progressVisibility = false
                        withContext(Dispatchers.Main) {
                            requireFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment, PasswordFragment().apply {
                                    arguments = bundleOf(
                                        "username" to edtUsername.text.toString(),
                                        "auth_client" to authClient,
                                        "params" to it.body
                                    )
                                }).commitAllowingStateLoss()
                        }
                    }, {
                        withContext(Dispatchers.Main) {
                            btn.progressVisibility = false
                            it.toToast(requireContext()).show()
                            Holder.callback?.onException(it)
                        }
                    })

                }
            }
        }
    }
}

