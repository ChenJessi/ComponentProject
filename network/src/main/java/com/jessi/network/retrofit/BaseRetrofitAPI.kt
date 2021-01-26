package com.jessi.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class BaseRetrofitAPI {
    companion object{
        private val retrofitHashMap = mutableMapOf<String, Retrofit>()
    }
    fun <T> getService(service : Class<T>, baseUrl: String): T {
        retrofitHashMap[baseUrl + service.name]?.let { return it.create(service) }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
        return setRetrofitBuilder(retrofitBuilder).build().create(service)
    }

    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    abstract fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder().run {
        var builder = OkHttpClient.Builder()
        builder = setHttpClientBuilder(builder)
        builder.build()
    }


}