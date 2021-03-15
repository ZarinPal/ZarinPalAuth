package com.zarinpal.auth;

public interface Callback {
    void onIssueAccessToken(String typeToken, String accessToken, String refreshToken, long expireIn);

    void onException(Throwable throwable);
}