package com.zarinpal.auth.view.register

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.midleware.RegistrationMiddleware
import com.zarinpal.auth.tools.toToast
import com.zarinpal.auth.view.login.OtpFragment
import com.zarinpal.provider.core.ButtonProgress
import com.zarinpal.provider.core.Font
import com.zarinpal.provider.core.ViewPumper
import com.zarinpal.provider.core.toTypeface

internal class RegistrationFragment : Fragment(R.layout.registration_auth_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewPumper.pump(view, Font.Light)

        val username = arguments?.getString("username")
        val edtName = view.findViewById<EditText>(R.id.edt_name)
        val edtFamily = view.findViewById<EditText>(R.id.edt_family)



        view.findViewById<TextView>(R.id.txt_message).apply {
            typeface = Font.Bold.toTypeface(requireContext())
        }
        view.findViewById<TextView>(R.id.txt_details).apply {
            text = String.format(text.toString(), username)
        }

        view.findViewById<ButtonProgress>(R.id.btn).apply {
            setOnClickListener {

                val name = edtName.text.toString()
                val family = edtFamily.text.toString()


                if (name.isEmpty()) {

                    return@setOnClickListener
                }

                if (family.isEmpty()) {


                    return@setOnClickListener
                }


                progressVisibility = true
                RegistrationMiddleware(name, family, username!!)
                    .asLiveData()
                    .observe(this@RegistrationFragment, Observer {
                        it.getOrElse {
                            it.toToast(requireContext()).show()
                            Holder.callback?.onException(it)
                            return@Observer
                        }.let {
                            requireFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment, OtpFragment()
                                    .apply {
                                        arguments = bundleOf(
                                            "username" to username,
                                            "auth_client" to Holder.authClient,
                                            "params" to it.body
                                        )
                                    }).commitAllowingStateLoss()
                        }
                    })

            }
        }
    }
}