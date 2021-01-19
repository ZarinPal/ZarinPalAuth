package com.zarinpal.auth.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zarinpal.Request
import com.zarinpal.ZarinPalAuth
import com.zarinpal.builder.ZarinPalAuthPresentation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CLIENT_ID = ""
        val CLIENT_SECRET = ""
        val GRANT_TYPE = ""
        val SCOPE = ""




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


//        ZarinPalAuth.Builder(this,)
//            .setMessage("آلودگی هوا و خشکی پوست دو عامل اصلی بیماری های پوستی می باشند.")
//            .build()
//            .start(object : ZarinPalAuth.Callback {
//                override fun onIssueAccessToken(
//                    typeToken: String?,
//                    accessToken: String?,
//                    refreshToken: String?,
//                    expireIn: Long
//                ) {
//                    Log.i("TAG token", accessToken)
//                }
//
//                override fun onException(throwable: Throwable?) {
//
//                }
//
//            })
    }
}