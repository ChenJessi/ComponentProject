package com.jessi.network.response

data class  HttpResult<T>(var data :T , var code : Int = 0, var message: String = "")