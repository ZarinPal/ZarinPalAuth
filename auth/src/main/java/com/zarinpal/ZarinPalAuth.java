package com.zarinpal;


import android.content.Context;

import com.zarinpal.builder.Appearance;
import com.zarinpal.builder.ContextBuilder;


public class ZarinPalAuth {


    private ZarinPalAuth() {
    }


    public static ContextBuilder with(Context context) {
        synchronized (ZarinPalAuth.class) {
            return new ContextBuilder(context);
        }
    }

}
