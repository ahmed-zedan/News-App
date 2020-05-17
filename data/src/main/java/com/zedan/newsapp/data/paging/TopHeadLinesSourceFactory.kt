package com.zedan.newsapp.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import com.zedan.newsapp.data.internet.ServicesApi
import kotlinx.coroutines.CoroutineScope

class TopHeadLinesSourceFactory(
    private val servicesApi: ServicesApi,
    private val country: String,
    private val category: String,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, ResponseTopHeadLines.Article>(){

    private val _sourceLiveData = MutableLiveData<TopHeadLinesDataSource>()
    val sourceLiveData : LiveData<TopHeadLinesDataSource> get() = _sourceLiveData

    override fun create(): DataSource<Int, ResponseTopHeadLines.Article> {
        val source = TopHeadLinesDataSource(servicesApi, country, category, scope)
        _sourceLiveData.postValue(source)
        return source
    }
}