package com.jessi.webview.remotewebview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.gson.Gson
import com.jessi.webview.remotewebview.callback.WebViewCallBack
import com.jessi.webview.remotewebview.commanddispatcher.CommandDispatcher
import com.jessi.webview.remotewebview.settings.WebViewDefaultSettings
import com.jessi.webview.remotewebview.webviewclient.JeWebViewClient


const val CONTENT_SCHEME = "file:///android_asset/"

private const val TAG = "BaseWebView"
open class BaseWebView(var mContext : Context, attrs : AttributeSet? = null) : WebView(mContext, attrs),
    JeWebViewClient.WebViewTouch {

    private var webViewCallBack : WebViewCallBack? = null
    private var mHeaders = mutableMapOf<String, String>()

    private var mTouchByUser = false
    init {
        WebViewDefaultSettings.setSettings(this)
        addJavascriptInterface(this, "webview")
        CommandDispatcher.initAidlConnect(mContext)
    }

    /**
     * 注册回调
     */
    fun registeredWebViewCallBack(webViewCallBack : WebViewCallBack){
        this.webViewCallBack = webViewCallBack
        webViewClient = JeWebViewClient(this, webViewCallBack, mHeaders, this)
    }

    fun setHeaders(mHeaders : MutableMap<String, String>){
        this.mHeaders = mHeaders
    }

    @JavascriptInterface
    fun post(cmd : String,  param : String){
        post {
            try {
                if (webViewCallBack != null){
                    CommandDispatcher.exec(mContext, cmd, param, this@BaseWebView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadUrl(url: String) {
        super.loadUrl(url, mHeaders)
        Log.e(TAG, "WebView loadUrl:  $url")
        resetAllStateInternal(url)
    }


    fun handleCallback(response : String?){
        if (!response.isNullOrEmpty()){
            val trigger = "javascript:dj.callback($response)"
            evaluateJavascript(trigger, null)
        }
    }

    fun loadJS(cmd : String, param: Any){
        val trigger = "javascript:" + cmd + "(" + Gson().toJson(param).toString() + ")"
        evaluateJavascript(trigger, null)
    }

    fun dispatchEvent(name : String){
        val param = mutableListOf(Pair("name", name))
        loadJS("dj.dispatchEvent", param)
    }

    private fun resetAllStateInternal(url: String){
        if (url.startsWith("javascript:")){
            return
        }
        resetAllState()
    }

    /**
     * 加载url时重置 touch 状态
     */
    private fun resetAllState(){
        mTouchByUser = false
    }


    override fun isTouchByUser(): Boolean {
        return mTouchByUser
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mTouchByUser = true
            }
        }
        return super.onTouchEvent(event)
    }
}