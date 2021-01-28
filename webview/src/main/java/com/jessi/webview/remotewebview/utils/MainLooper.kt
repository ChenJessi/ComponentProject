package com.jessi.webview.remotewebview.utils

import android.os.Handler
import android.os.Looper

class MainLooper private constructor(): Handler(Looper.getMainLooper()) {

    companion object{
        val instance by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ MainLooper() }
    }


    fun runOnUiThread(runnable: Runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run()
        } else {
            instance.post(runnable)
        }
    }
}