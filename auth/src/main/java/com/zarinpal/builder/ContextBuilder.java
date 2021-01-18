package com.zarinpal.builder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.zarinpal.Request;


public class ContextBuilder {

    private final FragmentManager fm;
    private final String appName;
    private Drawable icon;

    public ContextBuilder(Context context) {

        if (!(context instanceof FragmentActivity)) {
            throw new IllegalArgumentException("Context must instance of FragmentActivity");
        }

        fm = ((FragmentActivity) context).getSupportFragmentManager();
        appName = context.getApplicationInfo()
                .loadLabel(context.getPackageManager())
                .toString();


        try {
            icon = context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


    public AppearanceBuilder byRequest(Request request) {
        return new AppearanceBuilder(new RequestBuilder(request, fm, appName, icon));
    }


}
