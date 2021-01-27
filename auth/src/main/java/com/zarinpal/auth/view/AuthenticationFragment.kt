package com.zarinpal.auth.view

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.auth.view.login.InitializerFragment
import com.zarinpal.provider.core.Font
import com.zarinpal.provider.core.ViewPumper
import com.zarinpal.provider.core.toTypeface
import com.zarinpal.auth.view.base.BaseAuthBottomSheet


internal class AuthenticationFragment : BaseAuthBottomSheet() {

    lateinit var txtMessage: AppCompatTextView

    override fun onCreateView(view: View) {

        ViewPumper.pump(view, Font.Light)



        view.findViewById<AppCompatImageView>(R.id.icon)
            .setImageDrawable(Holder.drawable)

        view.findViewById<AppCompatTextView>(R.id.txt_app_name).apply {
            text = arguments?.getString("app_name" )
            typeface = Font.Bold.toTypeface(requireContext())

        }
        txtMessage = view.findViewById<AppCompatTextView>(R.id.txt_message).apply {
            val message = arguments?.getString("message", text.toString())
            text = message

        }

        childFragmentManager
            .beginTransaction()
            .addToBackStack(InitializerFragment::class.java.simpleName)
            .replace(
                R.id.fragment,
                InitializerFragment().apply {
                    arguments = this@AuthenticationFragment.arguments
                })
            .commitAllowingStateLoss()
    }

    override fun getLayout(viewGroup: ViewGroup?) = R.layout.parent_auth_bottosheet

}

