package com.zedan.newsapp.data.repository

import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.zedan.newsapp.data.entities.Listing
import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import com.zedan.newsapp.data.internet.RetrofitClient
import com.zedan.newsapp.data.paging.TopHeadLinesSourceFactory
import com.zedan.newsapp.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineScope

class TopHeadLinesRepository
    : UseCase<TopHeadLinesRepository.Param, Listing<ResponseTopHeadLines.Article>>() {

    private val pageSize = 20

    override suspend fun doWork(
        param: Param,
        resultAction: suspend (result: Listing<ResponseTopHeadLines.Article>) -> Unit
    ) {
        val sourceFactory = TopHeadLinesSourceFactory(
            category = param.category,
            country = param.country,
            scope = param.scope,
            servicesApi = RetrofitClient.retrofitService
        )
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setMaxSize(100)
            .build()
        val livePagedList = sourceFactory.toLiveData(config = config)
        val refreshState = sourceFactory.sourceLiveData.switchMap { it.initialLoad }
        val listing = Listing(
            pagedList = livePagedList,
            refreshStatus = refreshState,
            networkStatus = sourceFactory.sourceLiveData.switchMap { it.networkStatus },
            refresh = {sourceFactory.sourceLiveData.value?.invalidate()} ,
            retry = {sourceFactory.sourceLiveData.value?.retryAllFailed()}
        )
        resultAction.invoke(listing)
    }

    data class Param(val category : String, val country : String, val scope: CoroutineScope)
}