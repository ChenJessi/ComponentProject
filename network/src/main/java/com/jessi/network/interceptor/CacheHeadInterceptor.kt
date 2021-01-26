package com.jessi.network.interceptor

import android.content.Context
import com.jessi.network.ContextProvider
import okhttp3.Interceptor
import okhttp3.Response


class CacheHeadInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            ContextProvider.getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE).edit().apply {
                putStringSet("cookie", cookies)
                commit()
            }
        }
        return originalResponse
    }
}