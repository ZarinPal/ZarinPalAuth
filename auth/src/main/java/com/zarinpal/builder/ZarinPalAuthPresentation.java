package com.zarinpal.builder;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.zarinpal.ZarinPalAuth;
import com.zarinpal.auth.Callback;
import com.zarinpal.auth.Holder;

public class ZarinPalAuthPresentation {

    private final RequestBuilder builder;

    public ZarinPalAuthPresentation(RequestBuilder requestBuilder) {
        this.builder = requestBuilder;
    }

    public void start(@NonNull Callback callback) {
        Holder.INSTANCE.setCallback(callback);
        Holder.INSTANCE.setDrawable(builder.getIcon());
        Holder.INSTANCE.setAuthClient(builder.getAuthType().toSerialiaze());
        builder.getAppearance().appear(builder.getFragmentManager(), getBundle());
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("app_name", builder.getAppName());
        bundle.putInt("night_mode", builder.getNightMode());
        bundle.putInt("style", builder.getStyleableRes());
        bundle.putString("message", builder.getMessage());
        return bundle;


    }
}

