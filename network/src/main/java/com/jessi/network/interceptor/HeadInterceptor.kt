package com.jessi.network.interceptor

import android.content.Context
import com.jessi.network.ContextProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 头部拦截器
 */
class HeadInterceptor(private val headers: MutableMap<String, String>? = null) : Interceptor {

    val cookies : MutableSet<String>? =
        ContextProvider.getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE).getStringSet("cookie", null)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder().apply {
                addHeader("os", "android")
            }
        if (cookies != null) {
            for (cookie in cookies) {
                builder.addHeader("cookie", cookie)
            }
        }

        if (headers != null && headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                headers[headerKey]?.let {
                    builder.addHeader(headerKey, it)
                }
            }
        }
        //请求信息
        return chain.proceed(builder.build())
    }
}