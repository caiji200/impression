package com.me.impression.exception

class ServerException(
    var status: Int,
    var errorCode: String,
    var errorMsg: String
) : RuntimeException() 