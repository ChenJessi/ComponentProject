package com.jessi.webview.remotewebview

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.jessi.webview.remotewebview.callback.WebViewCallBack
import com.jessi.webview.remotewebview.commanddispatcher.CommandDispatcher
import com.jessi.webview.remotewebview.settings.WebViewDefaultSettings
import com.jessi.webview.remotewebview.webviewclient.JeWebViewClient


const val CONTENT_SCHEME = "file:///android_asset/"

class BaseWebView(val mContext : Context, attrs : AttributeSet? = null) : WebView(mContext, attrs),
    JeWebViewClient.WebViewTouch {

    private var webViewCallBack : WebViewCallBack? = null
    private var mHeaders = mutableMapOf<String, String>()

    init {
        WebViewDefaultSettings.setSettings(this)
        addJavascriptInterface(this, "webview")
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
            if (webViewCallBack != null){
                CommandDispatcher.exec(mContext, cmd, param, this@BaseWebView)
            }
        }
    }

    override fun isTouchByUser(): Boolean {

    }
}