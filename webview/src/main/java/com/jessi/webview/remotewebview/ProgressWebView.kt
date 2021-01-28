package com.jessi.webview.remotewebview

import android.content.Context
import android.util.AttributeSet
import com.jessi.webview.remotewebview.webchromeclient.JeWebChromeClient

/**
 *
 */
class ProgressWebView(mContext : Context, attrs : AttributeSet? = null) : BaseWebView(mContext, attrs) {

    init {
        webChromeClient = JeWebChromeClient()
    }
}