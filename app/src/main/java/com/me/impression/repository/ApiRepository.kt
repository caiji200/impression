package com.me.impression.repository

import com.me.impression.entity.TranslateResult
import com.me.impression.service.ApiRemoteSource
import com.me.impression.service.ApiService
import com.me.impression.service.ApiSource
import io.reactivex.Observable

/**
 * @author: Jie Cai
 * @date: 2021-03-14
 * @desc:
 * */
class ApiRepository (private val mSource:ApiSource): ApiSource {

    companion object {
        @Volatile
        private var sInstance: ApiRepository? = null

        fun instance(service: ApiService): ApiRepository {
            if (sInstance == null) {
                synchronized(ApiRepository::class) {
                    if (sInstance == null) {
                        sInstance = ApiRepository(ApiRemoteSource.getInstance(service))
                    }
                }
            }
            return sInstance!!
        }
    }

    override fun translate(params: Map<String, String>): Observable<TranslateResult> {
        return mSource.translate(params)
    }
}