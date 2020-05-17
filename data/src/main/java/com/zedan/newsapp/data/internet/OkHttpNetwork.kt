package com.zedan.newsapp.data.internet

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpNetwork {
    init {
        System.loadLibrary("api-keys")
    }

    fun getAuth() : Interceptor{
        return Interceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer ${getApiKey()}")
                .build()
            it.proceed(request)
        }
    }

    fun getLogging() : Interceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private external fun getApiKey() : String
}