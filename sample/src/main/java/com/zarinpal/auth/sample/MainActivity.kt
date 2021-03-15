package com.zarinpal.auth.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zarinpal.Request
import com.zarinpal.ZarinPalAuth
import com.zarinpal.auth.Callback
import com.zarinpal.auth.tools.start
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
            .start(object : Callback {
                override fun onIssueAccessToken(
                    typeToken: String?, accessToken: String?, refreshToken: String?, expireIn: Long
                ) {
                    println(accessToken)
                }

                override fun onException(throwable: Throwable?) {

                }
            })


        //Use by Coroutine
        GlobalScope.launch {
            runCatching {
                ZarinPalAuth.with(this@MainActivity)
                    .byRequest(Request.asPasswordGrant(GRANT_TYPE, CLIENT_SECRET, CLIENT_ID, SCOPE))
                    .asBottomSheet()
                    .setMessage("Message")
                    .make()
                    .start()
            }.getOrElse {
                println(it.message)
                return@launch
            }.let {
                println(it["accessToken"])
            }

        }


    }

}