package com.jessi.webview.remotewebview.webviewclient

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import com.jessi.webview.R
import com.jessi.webview.remotewebview.CONTENT_SCHEME
import com.jessi.webview.remotewebview.callback.WebViewCallBack

private const val TAG = "JeWebViewClient"

private const val SCHEME_SMS = "sms:"
class JeWebViewClient(val webView: WebView,
                      val webViewCallBack: WebViewCallBack,
                      val headers: MutableMap<String, String>,
                      val mWebviewTouch: WebViewTouch) : WebViewClient() {

    private var isReady : Boolean = false

    fun isReady() : Boolean = isReady



    interface WebViewTouch{
        fun isTouchByUser() : Boolean
    }


    /**
     * url 重定向会执行此方法以及点击页面某些链接也会执行此方法
     *
     * @return true: 表示当前url已经加载完成，即使url还会重定向都不会再进行加载，
     *         false： 表示此url默认由系统进行处理，该重定向还是重定向，直到加载完成
     */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url == null){
            return true
        }
        Log.e(TAG, "shouldOverrideUrlLoading: url: $url")
        // 当前链接的重定向，通过是否发生过点击行为来判断
        if (!mWebviewTouch.isTouchByUser()){
            return super.shouldOverrideUrlLoading(view, url)
        }
        // 如果链接跟当前链接一样，表示刷新
        if (webView.url == url){
            return super.shouldOverrideUrlLoading(view, url)
        }
        if (handleLinked(url)){
            return true
        }
        // 控制页面中点开新的链接在当前 WebView中打开
        view?.loadUrl(url, headers);
        return true
    }


    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.i(TAG, "shouldOverrideUrlLoading:  url : ${request?.url}")
        // 当前链接的重定向
        if (!mWebviewTouch.isTouchByUser()) {
            return super.shouldOverrideUrlLoading(view, request);
        }
        // 如果链接跟当前链接一样，表示刷新
        if (webView.url == request?.url.toString()) {
            return super.shouldOverrideUrlLoading(view, request);
        }
        if (handleLinked(request?.url.toString())) {
            return true;
        }
        // 控制页面中点开新的链接在当前webView中打开
        view?.loadUrl(request?.url.toString(), headers)
        return true
    }


    /**
     * 支持电话、短信、邮件、地图跳转
     */
    private fun handleLinked(url: String) : Boolean{
        if (url.startsWith(WebView.SCHEME_TEL)
            || url.startsWith(SCHEME_SMS)
            || url.startsWith(WebView.SCHEME_MAILTO)
            || url.startsWith(WebView.SCHEME_GEO)
        ) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                webView.context.startActivity(intent)
            } catch (ignored: ActivityNotFoundException) {
                ignored.printStackTrace()
            }
            return true
        }
        return false
    }


    override fun onPageFinished(view: WebView?, url: String?) {
        Log.e(TAG, "onPageFinished:  url : $url")
        if (url?.startsWith(CONTENT_SCHEME) == true) {
            isReady = true
        }
        webViewCallBack.pageFinished(url)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        Log.e(TAG, "onPageStarted:  url : $url")
        webViewCallBack.pageStarted(url)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return shouldInterceptRequest(view, request?.url.toString())
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        return null
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        Log.e(TAG, "onReceivedError:  errorCode : $errorCode   description : $description")
        webViewCallBack.onError()
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        // google pay 渠道需要弹窗提示用户
        var channel = ""
        if (!TextUtils.isEmpty(channel) && channel == "play.google.com") {
            val builder: AlertDialog.Builder = AlertDialog.Builder(webView.context)
            var message = webView.context.getString(R.string.ssl_error)
            when (error!!.primaryError) {
                SslError.SSL_UNTRUSTED -> message =
                    webView.context.getString(R.string.ssl_error_not_trust)
                SslError.SSL_EXPIRED -> message =
                    webView.context.getString(R.string.ssl_error_expired)
                SslError.SSL_IDMISMATCH -> message =
                    webView.context.getString(R.string.ssl_error_mismatch)
                SslError.SSL_NOTYETVALID -> message =
                    webView.context.getString(R.string.ssl_error_not_valid)
            }
            message += webView.context.getString(R.string.ssl_error_continue_open)
            builder.setTitle(R.string.ssl_error)
            builder.setMessage(message)
            builder.setPositiveButton(R.string.continue_open, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    handler?.proceed()
                }
            })
            builder.setNegativeButton(R.string.cancel, object : DialogInterface.OnClickListener {
                 override fun onClick(dialog: DialogInterface?, which: Int) {
                    handler?.cancel()
                }
            })
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            handler?.proceed()
        }

    }

}