package com.zarinpal.auth.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zarinpal.Request
import com.zarinpal.ZarinPalAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CLIENT_ID = "3"
        val CLIENT_SECRET = "BjHEBKZu3X3n2h6oeCwyq7uem5dces7hmKSTRwiC"
        val GRANT_TYPE = "password"
        val SCOPE = "*"





        ZarinPalAuth.with(this)
            .byRequest(Request.asPasswordGrant(GRANT_TYPE, CLIENT_SECRET, CLIENT_ID, SCOPE))
            .asBottomSheet()
            .setMessage("من دارم میام")
            .make()
            .start(object : ZarinPalAuth.Callback {
                override fun onIssueAccessToken(
                    typeToken: String?,
                    accessToken: String?,
                    refreshToken: String?,
                    expireIn: Long
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