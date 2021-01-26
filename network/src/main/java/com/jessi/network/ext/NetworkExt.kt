package com.jessi.network.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jessi.network.exception.ExceptionHandle
import com.jessi.network.response.HttpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

fun <T> ViewModel.request(block : suspend () -> HttpResult<T>,
                          success :(T) -> Unit,
                          failure :(Throwable) -> Unit = {}){

    viewModelScope.launch (Dispatchers.Main){
        runCatching {
            withContext(Dispatchers.IO){ block() }
        }.onSuccess {
            if (it.code == 200){
                success(it.data)
            }else{
                failure(Throwable(it.message))
            }
        }.onFailure {
            failure(it)
        }
    }
}