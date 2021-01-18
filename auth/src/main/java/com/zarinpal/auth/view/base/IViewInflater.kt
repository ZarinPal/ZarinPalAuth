package com.zarinpal.provider.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by ImanX.
 * Asghar | Copyrights 2019 ZarinPal Crop.
 */
interface IViewInflater {
    fun onCreateView(view: View)
    @LayoutRes
    fun getLayout(viewGroup: ViewGroup?): Int
}