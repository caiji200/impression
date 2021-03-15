package com.me.impression.net

import com.me.impression.common.AppConfig
import com.me.impression.utils.L
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

/**
 * @author:Jie Cai
 * @desc: retrofit interceptor for logging
 */
class HttpLoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.method()
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body() is FormBody) {
                val body = request.body() as FormBody?
                for (i in 0 until body!!.size()) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                if (sb.isNotEmpty()) {
                    sb.delete(sb.length - 1, sb.length)
                }
                L.d(
                    AppConfig.LOG_TAG, String.format(
                        "post %s on %s %n%s %n%s",
                        request.url(), chain.connection(), request.headers(), sb.toString()
                    )
                )
            } else if (request.body() is RequestBody) {
                L.d(AppConfig.LOG_TAG, "post on url ==> " + request.url())
            }
        } else {
            L.d(
                AppConfig.LOG_TAG, String.format(
                    "get %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()
                )
            )
        }
        val response = chain.proceed(request)
        val responseBody = response.peekBody(1024 * 1024.toLong())
        val resultJson = responseBody.string()
        L.json(AppConfig.LOG_TAG, "response :",resultJson.toString())
        return response
    }
}