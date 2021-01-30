package com.jessi.webview.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.jessi.webview.R
import com.jessi.webview.command.CommandsManager
import com.jessi.webview.commands.ToastCommand
import com.jessi.webview.remotewebview.ProgressWebView
import com.jessi.webview.remotewebview.callback.WebViewCallBack
import com.jessi.webview.utils.INTENT_TAG_URL


const val ACCOUNT_INFO_HEADERS = "account_header"

private const val TAG = "BaseWeViewFragment"
internal abstract class BaseWeViewFragment : Fragment(), WebViewCallBack {

    private var accountInfoHeaders : MutableMap<String, String>? = null
    private var webUrl : String? = null
    private var webView : ProgressWebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments?.apply {
             webUrl = getString(INTENT_TAG_URL)
            if (containsKey(ACCOUNT_INFO_HEADERS)){
                val str = getString(ACCOUNT_INFO_HEADERS)
                accountInfoHeaders = Gson().fromJson<MutableMap<String, String>>(str, MutableMap::class.java)
            }
        }
        CommandsManager.registerCommand(ToastCommand())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_common_webview, container, false)
         webView = view.findViewById<ProgressWebView>(R.id.webView)
        accountInfoHeaders?.apply {
            webView?.setHeaders(this)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView?.registeredWebViewCallBack(this)
        loadUrl()
    }

    private fun loadUrl(){
        if (webUrl.isNullOrEmpty()){
            webView?.loadUrl(webUrl!!)
        }else{
            Toast.makeText(activity, "WebView url is can't empty!", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onResume() {
        super.onResume()
        webView?.dispatchEvent("pageResume")
        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView?.dispatchEvent("pagePause")
        webView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        webView?.dispatchEvent("pageStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView?.apply {
            dispatchEvent("pageDestroy")
            clearWebView(this)
        }
    }



    override fun pageStarted(url: String?) {

    }

    override fun pageFinished(url: String?) {
        TODO("Not yet implemented")
    }

    override fun onError() {
        TODO("Not yet implemented")
    }

    override fun onShowFileChooser(
        cameraIntent: Intent?,
        filePathCallback: ValueCallback<Array<Uri?>?>?
    ) {
        TODO("Not yet implemented")
    }



    private fun clearWebView(webView : WebView?){
        var w = webView
        if (Looper.myLooper() != Looper.getMainLooper()){
            return
        }
        w?.apply {
            stopLoading()
            handler?.removeCallbacksAndMessages(null)
            removeAllViews()
            parent?.let {
                (it as ViewGroup)?.removeView(this)
            }
            webChromeClient = null
//            webViewClient = null
            tag = null
            clearHistory()
            destroy()
        }
        w = null
    }


}