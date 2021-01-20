package com.zarinpal.auth

import android.graphics.drawable.Drawable
import com.zarinpal.ZarinPalAuth

object Holder {
    internal var callback: ZarinPalAuth.Callback? = null
    internal var drawable: Drawable? = null
    internal var authClient: String? = null

    fun setCallback(callback: ZarinPalAuth.Callback) {
        this.callback = callback
    }

    fun setDrawable(drawable: Drawable) {
        this.drawable = drawable
    }


    fun setAuthClient(authClient: String) {
        this.authClient = authClient
    }

}