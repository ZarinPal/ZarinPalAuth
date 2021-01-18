package com.zarinpal.auth.view

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.zarinpal.auth.Holder
import com.zarinpal.auth.R
import com.zarinpal.provider.view.bottomSheets.BaseBottomSheet


internal class AuthenticationFragment : BaseBottomSheet() {

    override fun onCreateView(view: View) {

        view.findViewById<AppCompatImageView>(R.id.icon)
            .setImageDrawable(Holder.drawable)

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

    override fun getLayout(viewGroup: ViewGroup?) = R.layout.native_auth_bottosheet

}

