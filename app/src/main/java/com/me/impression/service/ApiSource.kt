package com.me.impression.service

import com.me.impression.entity.TranslateResult
import io.reactivex.Observable


interface ApiSource {

    fun translate(params:Map<String,String>): Observable<TranslateResult>
}
