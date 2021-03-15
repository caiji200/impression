package com.me.impression.service

import com.me.impression.base.BaseApiRemoteSource
import com.me.impression.common.URL
import com.me.impression.entity.TranslateResult
import io.reactivex.Observable

/**
 * @author:Jie Cai
 * @desc：public api repository
 */
class ApiRemoteSource(private val mApi: ApiService): BaseApiRemoteSource(),ApiSource {

    companion object {
        @Volatile
        private var sInstance: ApiRemoteSource? = null

        fun getInstance(service:ApiService): ApiSource {
            if (sInstance == null) {
                synchronized(ApiRemoteSource::class) {
                    if (sInstance == null) {
                        sInstance = ApiRemoteSource(service)
                    }
                }
            }
            return sInstance!!
        }
    }


    override fun translate(params: Map<String, String>): Observable<TranslateResult> {
        return Observable.just(params)
            .map<Map<String, String>> {
                var apiParams:MutableMap<String,String> = HashMap()
                apiParams["q"] = it["q"] ?: ""
                apiParams["from"] = it["from"] ?: ""
                apiParams["to"] = it["to"] ?: ""
                apiParams["appid"] = it["appid"]?:""
                apiParams["salt"] = it["salt"]?:""
                apiParams["sign"] = it["sign"]?:""
                apiParams
            }.filter {
                checkParams(it)
            }.flatMap {
                mApi.translate(URL.TRANSLATE_URL, it)
            }.map {
                it
            }.onErrorResumeNext(HttpResponseErrorInterceptor<TranslateResult>())
    }
}