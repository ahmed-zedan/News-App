package com.zedan.newsapp.data.internet

import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicesApi {

    @GET("top-headlines")
    fun getTopHeadLinesAsync(
        @Query("country") country : String,
        @Query("category") category: String,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int
    ) : Deferred<ResponseTopHeadLines>

}