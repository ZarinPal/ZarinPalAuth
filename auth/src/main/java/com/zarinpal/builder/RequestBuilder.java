package com.zarinpal.builder;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import com.zarinpal.Request;
import com.zarinpal.auth.grant.IAuthRequestGrant;

public class RequestBuilder {

    private final FragmentManager fragmentManager;
    private final String appName;
    private final Drawable icon;

    private Appearance appearance;
    private IAuthRequestGrant authType;
    private String message;
    @AppCompatDelegate.NightMode
    private int nightMode;
    @StyleRes
    private int styleableRes;


    public RequestBuilder(@NonNull Request request, FragmentManager fm, String appName, Drawable icon) {
        this.authType = request.getAuthRequestType();
        this.fragmentManager = fm;
        this.appName = appName;
        this.icon = icon;

    }

    public RequestBuilder setRequest(Request request) {
        this.authType = request.getAuthRequestType();
        return this;
    }

    public RequestBuilder setMessage(String message) {
        if (message.length() > 60) {
            throw new IllegalArgumentException("message can't more than 100 character.");
        }
        this.message = message;
        return this;
    }

    public RequestBuilder setAppearance(Appearance appearance) {
        this.appearance = appearance;
        return this;
    }

    public RequestBuilder setNightMode(int nightMode) {
        this.nightMode = nightMode;
        return this;
    }

    protected Appearance getAppearance() {
        return appearance;
    }

    protected FragmentManager getFragmentManager() {
        return fragmentManager;
    }


    protected String getAppName() {
        return appName;
    }

    protected Drawable getIcon() {
        return icon;
    }

    protected int getNightMode() {
        return nightMode;
    }

    protected int getStyleableRes() {
        return styleableRes;
    }

    protected String getMessage() {
        return message;
    }

    protected IAuthRequestGrant getAuthType() {
        return authType;
    }

    public ZarinPalAuthPresentation make() {
        return new ZarinPalAuthPresentation(this);
    }
}


//

