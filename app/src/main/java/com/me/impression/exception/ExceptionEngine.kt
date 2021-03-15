package com.me.impression.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionEngine {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    fun handleException(e: Throwable?): ApiException {
        val ex: ApiException
        return if (e is HttpException) {
            ex = ApiException(e, ERROR.HTTP_ERROR)
            when (e.code()) {
                REQUEST_TIMEOUT, GATEWAY_TIMEOUT -> ex.errorMessage =
                    "request timeout"
                INTERNAL_SERVER_ERROR, BAD_GATEWAY -> {
                    ex.errorMessage = "server exception"
                    ex.errorMessage = "request failed"
                }
                else -> ex.errorMessage = "request failed"
            }
            ex
        } else if (e is ServerException) {
            val resultException =
                e
            ex = ApiException(resultException, resultException.status)
            ex.errorMessage = resultException.errorMsg
            ex.errorCode = resultException.errorCode
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            ex = ApiException(e, ERROR.PARSE_ERROR)
            ex.errorCode = ERROR.PARSE_ERROR.toString()
            ex.errorMessage = "parse exception"
            ex
        } else if (e is ConnectException
            || e is SocketTimeoutException
            || e is UnknownHostException
        ) {
            ex = ApiException(e, ERROR.NETWORK_ERROR)
            ex.errorCode = ERROR.NETWORK_ERROR.toString()
            ex.errorMessage = "net unavailable，please check"
            ex
        } else {
            ex = ApiException(e, ERROR.UNKNOWN)
            ex.errorCode = ERROR.UNKNOWN.toString()
            ex.errorMessage = "unknown error"
            ex
        }
    }

    private object ERROR {
        const val UNKNOWN = 1000
        const val PARSE_ERROR = 1001
        const val NETWORK_ERROR = 1002
        const val HTTP_ERROR = 1003
    }
}