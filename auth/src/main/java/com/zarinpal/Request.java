package com.zarinpal;

import com.zarinpal.auth.grant.AuthorizationCode;
import com.zarinpal.auth.grant.PasswordGrant;
import com.zarinpal.auth.grant.IAuthRequestGrant;

public class Request {


    private final IAuthRequestGrant authRequestType;

    private Request(IAuthRequestGrant authRequestType) {
        this.authRequestType = authRequestType;
    }

    public static Request asPasswordGrant(
            String grantType,
            String clientSecret,
            String clientId,
            String scope) {
        return new Request(new PasswordGrant(grantType, clientSecret, clientId, scope));
    }

    public static Request asAuthorizationCode(String url) {
        return new Request(new AuthorizationCode());
    }

    public IAuthRequestGrant getAuthRequestType() {
        return this.authRequestType;
    }
}

