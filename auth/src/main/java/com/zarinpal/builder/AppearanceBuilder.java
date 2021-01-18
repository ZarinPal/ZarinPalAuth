package com.zarinpal.builder;

import androidx.annotation.IdRes;

public class AppearanceBuilder {

    private final RequestBuilder builder;

    public AppearanceBuilder(RequestBuilder builder) {
        this.builder = builder;
    }

    public RequestBuilder asSheet(@IdRes int id) {
        return this.builder.setAppearance(new Appearance.SheetAppearance(id));
    }

    public RequestBuilder asBottomSheet() {
        return this.builder.setAppearance(new Appearance.BottomSheetAppearance());
    }
}
