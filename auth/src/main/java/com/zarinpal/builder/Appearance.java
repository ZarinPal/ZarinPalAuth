package com.zarinpal.builder;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;

import com.zarinpal.auth.Holder;
import com.zarinpal.auth.view.AuthenticationFragment;

public interface Appearance {
    void appear(FragmentManager fm, Bundle bundle);


    class SheetAppearance implements Appearance {

        private final int id;

        public SheetAppearance(@IdRes int id) {
            this.id = id;
        }

        @Override
        public void appear(FragmentManager fm, Bundle bundle) {
            AuthenticationFragment authenticationBottomSheet = new AuthenticationFragment();
            authenticationBottomSheet.setArguments(bundle);
            fm.beginTransaction()
                    .replace(id, authenticationBottomSheet)
                    .commitAllowingStateLoss();
        }
    }

    class BottomSheetAppearance implements Appearance {

        @Override
        public void appear(FragmentManager fm, Bundle bundle) {
            AuthenticationFragment authenticationBottomSheet = new AuthenticationFragment();
            authenticationBottomSheet.setArguments(bundle);
            authenticationBottomSheet.show(fm);
        }
    }

}



