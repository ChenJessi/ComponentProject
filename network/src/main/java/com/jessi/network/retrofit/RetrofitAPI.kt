package com.jessi.network.retrofit


import com.jessi.network.interceptor.HeadInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jessi.network.ContextProvider
import com.jessi.network.interceptor.CacheHeadInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

 open class RetrofitAPI : BaseRetrofitAPI() {
    /**
     * 连接超时时间，单位s
     */
    private  val DEFAULT_CONNECT_TIMEOUT = 10L

    /**
     * 读超时时间，单位s
     */
    private  val DEFAULT_READ_TIMEOUT = 10L

    /**
     * 写超时时间，单位s
     */
    private  val DEFAULT_WRITE_TIMEOUT = 10L


    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        }
    }

    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.apply {
            cache(Cache(File(ContextProvider.getContext().cacheDir, "cx_cache"), 10 * 1024 * 1024))
            connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(HeadInterceptor())
            addInterceptor(CacheHeadInterceptor())
            addInterceptor(getLogging())
        }
    }

    private fun getLogging() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}