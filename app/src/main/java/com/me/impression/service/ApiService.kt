package com.me.impression.service

import com.me.impression.entity.TranslateResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiService {

    /**
     * @description: translate
     * @param:
     * @return:
     */
    @GET
    fun translate(@Url url: String, @QueryMap params:Map<String,String>): Observable<TranslateResult>
}