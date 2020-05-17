package com.zedan.newsapp.data.paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import com.zedan.newsapp.data.internet.RetrofitClient
import com.zedan.newsapp.data.internet.ServicesApi
import com.zedan.newsapp.domain.entities.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopHeadLinesDataSource(
    private val servicesApi: ServicesApi,
    private val country: String,
    private val category: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ResponseTopHeadLines.Article>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStatus: LiveData<NetworkStatus> get() = _networkState

    private val _initialLoad = MutableLiveData<NetworkStatus>()
    val initialLoad: LiveData<NetworkStatus> get() = _initialLoad

    companion object {
        private const val START_PAGE = 1
        private val tag: String = this::class.java.simpleName
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            scope.launch(Default) {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResponseTopHeadLines.Article>
    ) {
        scope.launch(IO) {
            Log.e(
                tag,
                "enter in load initial method ${params.requestedLoadSize}, $START_PAGE, $category, $country"
            )
            _networkState.postValue(NetworkStatus.Loading)
            _initialLoad.postValue(NetworkStatus.Loading)
            Log.e(tag, "start from launch")
            try {
                val response =
                    getTopHeadLines(pageSize = params.requestedLoadSize, page = START_PAGE)
                response.message?.let {
                    Log.e(tag, "message error is $it")
                    _networkState.postValue(NetworkStatus.Error("error code ${response.code} \n $it"))
                    _initialLoad.postValue(NetworkStatus.Error("error code ${response.code} \n $it"))
                }
                val items = response.articles?.let {
                    _networkState.postValue(NetworkStatus.Success(it))
                    _initialLoad.postValue(NetworkStatus.Success(it))
                    it
                } ?: mutableListOf()
                Log.e(tag, "items size is ${items.size}")
                retry = null
                callback.onResult(items, null, START_PAGE + 1)

            } catch (t: Throwable) {
                retry = { loadInitial(params, callback) }
                val error = NetworkStatus.Error(t.message ?: "unknown error")
                _networkState.postValue(error)
                _initialLoad.postValue(error)
            }
        }

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResponseTopHeadLines.Article>
    ) {
        Log.e(tag, "enter in load after method")
        _networkState.postValue(NetworkStatus.Loading)
        scope.launch(IO) {
            try {
                val response =
                    getTopHeadLines(page = params.key, pageSize = params.requestedLoadSize)
                response.message?.let {
                    _networkState.postValue(NetworkStatus.Error("error code ${response.code} \n $it"))
                }
                retry = null
                val item = response.articles?.let {
                    _networkState.postValue(NetworkStatus.Success(it))
                    it
                } ?: mutableListOf()
                val key = if (response.articles.isNullOrEmpty()) null else params.key + 1
                callback.onResult(item, key)
            } catch (t: Throwable) {
                retry = { loadAfter(params, callback) }
                _networkState.postValue(NetworkStatus.Error(t.message ?: "unknown error"))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResponseTopHeadLines.Article>
    ) {

        TODO("Not yet implemented")
    }

    private suspend fun getTopHeadLines(page: Int, pageSize: Int) =
        withContext(IO) {
            servicesApi
                .getTopHeadLinesAsync(
                    country = country,
                    category = category,
                    page = page,
                    pageSize = pageSize
                ).await()
        }


}