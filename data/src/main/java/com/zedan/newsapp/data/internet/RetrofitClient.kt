package com.zedan.newsapp.data.internet

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    init {
        System.loadLibrary("api-keys")
    }

    private val mosh by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val okHttp by lazy {
        OkHttpClient.Builder()
            .addInterceptor(OkHttpNetwork.getAuth())
            .addInterceptor(OkHttpNetwork.getLogging())
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(500, TimeUnit.SECONDS)
            .writeTimeout(500, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(mosh))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttp)
            .baseUrl(getBaseLink())
            .build()
    }


    val retrofitService : ServicesApi by lazy {
        retrofit.create(ServicesApi::class.java)
    }

    private external fun getBaseLink() : String
}
