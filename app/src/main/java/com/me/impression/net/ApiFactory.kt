package com.me.impression.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.me.impression.common.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author:Jie Cai
 * @desc: Api Factory for http
 */
class ApiFactory private constructor() {

    private val retrofit: Retrofit
    private val client: OkHttpClient

    private fun createGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    private object SingletonHolder {
        val instance = ApiFactory()
    }

    fun <T> create(service: Class<T>?): T {
        return retrofit.create(service)
    }

    companion object {
        private const val DEFAULT_TIME_OUT: Long = 10 //timeout time
        private const val DEFAULT_READ_TIME_OUT: Long = 10
        private const val BASE_URL = "https://me.com.cn"
        fun instance(): ApiFactory {
            return SingletonHolder.instance
        }
    }

    init {
        val builder = OkHttpClient.Builder()
            .connectTimeout(
                DEFAULT_TIME_OUT,
                TimeUnit.SECONDS
            )
            .readTimeout(
                DEFAULT_READ_TIME_OUT,
                TimeUnit.SECONDS
            )
        if (AppConfig.SHOW_LOG) {
            builder.addInterceptor(HttpLoggingInterceptor())
        }
        client = builder.build()
        retrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .baseUrl(BASE_URL)
            .build()
    }
}