package com.zarinpal.auth.view

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.zarinpal.auth.R
import com.zarinpal.provider.view.bottomSheets.BaseBottomSheet


internal class AuthenticationByWebViewBottomSheet : BaseBottomSheet() {
    override fun onCreateView(view: View) {
        val webView = view.findViewById<WebView>(R.id.web_view)



    }

    override fun getLayout(viewGroup: ViewGroup?) = R.layout.webview_auth_bottomsheet

}