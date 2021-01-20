# ZarinPal Auth

You can always Access zarinpal user without implement login logic on Android Clients.
In the first step, ZarinPal Auth obtains `Request` and process it. ZarinPal Auth ables appears login screen as `BottomSheet` or `Sheet` on Your app.



To start working with ZarinPal Auth, you must to add its dependency to your `build.gradle` file:
```
dependencies {
   implementation 'com.zarinpal:auth:$latestVersion'
}
```

To get the latest version click [Here](https://github.com/ZarinPal/ZarinPalAuth/releases)!


# Sample :

```kotlin
  ZarinPalAuth.with(this)
            .byRequest(Request.asPasswordGrant(GRANT_TYPE, CLIENT_SECRET, CLIENT_ID, SCOPE))
            .asBottomSheet()
            .setMessage("Message")
            .make()
            .start(object : ZarinPalAuth.Callback {
                override fun onIssueAccessToken(
                    typeToken: String?, accessToken: String?, refreshToken: String?, expireIn: Long
                ) {
                    Log.i("TAG", accessToken)
                }

                override fun onException(throwable: Throwable?) {

                }
            })
```
