package com.jessi.webview.remotewebview.callback

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback

interface WebViewCallBack {
    fun pageStarted(url: String?)
    fun pageFinished(url: String?)
    fun onError()
    fun onShowFileChooser(cameraIntent: Intent?, filePathCallback: ValueCallback<Array<Uri?>?>?)
}