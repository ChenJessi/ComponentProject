package com.jessi.webview

import android.os.Bundle
import android.webkit.CookieManager
import com.google.gson.Gson
import com.jessi.webview.utils.INTENT_TAG_URL
import com.jessi.webview.view.ACCOUNT_INFO_HEADERS
import com.jessi.webview.view.BaseWeViewFragment

class WebViewFragment : BaseWeViewFragment(){

    companion object{
        fun newInstance(url : String, headers : MutableMap<String, String>? = null , isSyncToCookie : Boolean = true): WebViewFragment{
            val args = Bundle().apply {
                putString(INTENT_TAG_URL, url)
                putString(ACCOUNT_INFO_HEADERS, Gson().toJson(headers))
            }
            if (isSyncToCookie && headers!= null){
                syncCookie(url, headers)
            }
            val fragment = WebViewFragment()
            fragment.arguments = args
            return fragment
        }

        /**
         * 将cookie同步到WebView
         */
        fun syncCookie(url : String, headers: MutableMap<String, String>) : Boolean{
            val cookieManager = CookieManager.getInstance()
            headers.forEach {
                cookieManager.setCookie(url, "${it.key}=${it.value}")
            }
            val newCookie = cookieManager.getCookie(url)
            return newCookie.isNotEmpty()
        }
    }




}