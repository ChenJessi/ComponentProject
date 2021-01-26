package com.jessi.webview.remotewebview

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.jessi.webview.remotewebview.settings.WebViewDefaultSettings

class BaseWebView(mContext : Context, attrs : AttributeSet? = null) : WebView(mContext, attrs) {

    init {
        WebViewDefaultSettings.setSettings(this)
        addJavascriptInterface(this, "webview")
    }


    @JavascriptInterface
    fun post(cmd : String,  param : String){

    }
}