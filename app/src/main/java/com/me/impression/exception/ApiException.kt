package com.me.impression.exception

class ApiException(throwable: Throwable?, var status: Int) : Exception(throwable) {
    @JvmField
    var errorMessage: String? = null
    @JvmField
    var errorCode: String? = null
}