package com.jessi.webview.remotewebview.commanddispatcher

import android.content.Context
import android.util.Log
import android.webkit.WebView
import com.jessi.webview.IWebToMain
import kotlin.concurrent.thread


/**
 * WebView 请求分发
 */
object CommandDispatcher {
    // 跨进程通信接口
    var webAidlInterface : IWebToMain? = null


    fun initAidlConnect(context : Context){
        if (webAidlInterface != null){
            return
        }
        thread (start = true){
            Log.i("AIDL", "Begin to connect with main process")
            webAidlInterface = IWebToMain.Stub.asInterface(MainProcessConnector.instance(context).getIWebAidlInterface())
            Log.i("AIDL", "Connect success with main process")
        }
    }

    fun exec(context: Context, cmd : String, params : String, webView: WebView){

    }
}