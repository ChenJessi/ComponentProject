package com.jessi.webview.remotewebview.commanddispatcher

import android.content.Context
import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import com.jessi.webview.ICallbackFromMainToWeb
import com.jessi.webview.IWebToMain
import com.jessi.webview.command.CommandsManager
import com.jessi.webview.command.ResultBack
import com.jessi.webview.remotewebview.BaseWebView
import com.jessi.webview.remotewebview.utils.MainLooper
import com.jessi.webview.remotewebview.utils.NATIVE2WEB_CALLBACK
import kotlin.concurrent.thread


/**
 * WebView 请求分发
 */
private const val TAG = "CommandDispatcher"
object CommandDispatcher {
    // 跨进程通信接口
    var webAidlInterface : IWebToMain? = null

    private val gson = Gson()

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
        Log.i(TAG, "exec:   command : $cmd   params : $params ")
        try {
            if (CommandsManager.isWebViewProcessCommand(cmd)){
                val mapParams = gson.fromJson(params, MutableMap::class.java)
                CommandsManager.execWebViewProcessCommand(context, cmd, mapParams, object : ResultBack{
                    override fun onResult(status: Int, action: String, result: Any?) {
                        handleCallback(status, action, gson.toJson(result), webView)
                    }
                })
            }else{
                webAidlInterface?.handleWebAction(cmd, params, object : ICallbackFromMainToWeb.Stub(){
                    override fun onResult(responseCode: Int, actionName: String?, response: String?) {
                        handleCallback(responseCode, actionName, response, webView)
                    }
                })
            }
        } catch (e: Exception) {
            Log.e(TAG, "Command exec error!!!!", e)
        }
    }


    private fun handleCallback(responseCode : Int, actionName : String?, response : String?, webView: WebView){
        Log.d(TAG, "handleCallback:  action : $actionName, response : $response")
        MainLooper.instance.runOnUiThread(Runnable {
            val params = Gson().fromJson(response, MutableMap::class.java)
            if (params[NATIVE2WEB_CALLBACK]?.toString()?.isNotEmpty() == true){
                if (webView is BaseWebView){
                    webView.handleCallback(response)
                }
            }
        })
    }


}