package com.me.impression.base

import android.text.TextUtils
import com.google.gson.Gson
import com.me.impression.exception.ExceptionEngine
import com.me.impression.utils.L
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

open class BaseApiRemoteSource
{
    /**
     * @description: public params
     * @param:
     * @return:
     */
    fun publicParams(): Map<String, String> {
        val params:MutableMap<String,String> =  HashMap()
        return params
    }

    fun checkParams(params: Map<String, Any>?): Boolean {
        if (params == null) {
            return false
        }
        L.d("http params：\n")
        L.d("==================================================\n")
        val keySet = params.keys
        val keys = ArrayList(keySet)
        keys.sort()
        val urlParams = StringBuilder()
        for (key in keys) {
            if (!TextUtils.isEmpty(params[key].toString())) {
                urlParams.append(key)
                    .append("=")
                    .append(params[key])
                urlParams.append("&")
                L.d("║  \"" + key + "\":\"" + params[key] + "\"")
            }
        }
        var paramStr = ""
        if(!TextUtils.isEmpty(urlParams)){
            paramStr = urlParams.subSequence(0, urlParams.length - 1).toString()
        } else {
            L.d("║  \"")
            L.d("║  \"")
            L.d("║  \"")
        }
        L.d( "==================================================\n")
        L.d("commit params str : $paramStr")
        return true
    }

    fun checkParams(params: JSONObject):Boolean
    {
        if (params == null) {
            return false
        }
        L.d("http params：\n")
        L.d("==================================================\n")
        val keyIter = params.keys()

        val keys = ArrayList<String>()
        while(keyIter.hasNext()){
            keys.add(keyIter.next())
        }
        keys.sort()
        val urlParams = StringBuilder()
        for (key in keys) {
            if (!TextUtils.isEmpty(params[key].toString())) {
                urlParams.append(key)
                    .append("=")
                    .append(params[key])
                urlParams.append("&")
                L.d("║  \"" + key + "\":\"" + params[key] + "\"")
            }
        }
        var paramStr = ""
        if(!TextUtils.isEmpty(urlParams)){
            paramStr = urlParams.subSequence(0, urlParams.length - 1).toString()
        } else {
            L.d("║  \"")
            L.d("║  \"")
            L.d("║  \"")
        }
        L.d("==================================================\n")
        return true
    }

    fun convertToRequestBody(params:Map<String,Any>):RequestBody{
        var body = Gson().toJson(params)
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body)
    }

    inner class HttpResponseErrorInterceptor<T> : Function<Throwable, ObservableSource<T>> {
        @Throws(Exception::class)
        override fun apply(throwable: Throwable): ObservableSource<T> {
            L.d( "exception===>" + throwable.message)
            val stack = Throwable().stackTrace
            for (element in stack) {
                L.d(" |----$element")
            }
            return Observable.error(ExceptionEngine.handleException(throwable))
        }
    }
}
