package com.zarinpal.auth.controller

import org.json.JSONObject
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal open class HttpClient(private val request: Request) {


    suspend fun fetch() = suspendCoroutine<Response> { it ->
        try {
            (URL(request.Url).openConnection() as HttpsURLConnection).apply {
                requestMethod = request.method.value
                doInput = true
                doOutput = true

                request.contentType?.apply { request.header?.put(entry.first, entry.second) }
                request.header?.forEach {
                    addRequestProperty(
                        request.contentType?.entry?.first,
                        request.contentType?.entry?.second
                    )
                    addRequestProperty(it.key, it.value)
                }
                val outputStreamWriter =
                    OutputStreamWriter(outputStream)
                outputStreamWriter.write(getBody())
                outputStreamWriter.flush()



                connect()
                val content = if (responseCode == 200) {
                    String(inputStream.readBytes())
                } else {
                    String(errorStream.readBytes())
                }


                val response = Response(content, responseCode, headerFields, request)


                if (responseCode != 200) {
                    it.resumeWithException(HttpException(content, responseCode, response))
                } else {
                    it.resumeWith(Result.success(response))
                }

            }


        } catch (ex: Exception) {
            it.resumeWithException(ex)
        }

    }

    private fun encodeParam(data: String): String? {
        return try {
            URLEncoder.encode(data, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            return String()
        }
    }


    private fun getBody(): String {
        return when (request.contentType) {
            ContentType.ApplicationFormUrlEncoded -> getParamsByte(request.params!!)
            ContentType.ApplicationJson -> JSONObject(request.params.toString()).toString()
            else -> JSONObject(request.params.toString()).toString()
        }
    }

    private fun getParamsByte(params: Map<String, Any?>): String {
        val postData = StringBuilder()
        for ((key, value) in params) {
            if (postData.isNotEmpty()) {
                postData.append('&')
            }
            postData.append(this.encodeParam(key))
            postData.append('=')
            postData.append(this.encodeParam(value.toString()))
        }
        try {
            return postData.toString()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return String()
    }

}
