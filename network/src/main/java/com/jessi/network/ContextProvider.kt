package com.jessi.network

import android.content.Context

object ContextProvider {

    fun getContext() : Context{
        return AppContextProvider.mContext
    }
}