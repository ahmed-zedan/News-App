package com.zedan.newsapp.ui.home.tophead

import android.net.Network
import androidx.lifecycle.*
import com.zedan.newsapp.data.entities.Listing
import com.zedan.newsapp.data.entities.ResponseTopHeadLines.Article
import com.zedan.newsapp.data.repository.TopHeadLinesRepository.Param
import com.zedan.newsapp.domain.entities.NetworkStatus
import com.zedan.newsapp.domain.usecase.UseCase
import kotlinx.coroutines.launch

class TopHeadLinesViewModel(
    private val repo : UseCase<Param, Listing<Article>>
) : ViewModel(){

    private val _result = MutableLiveData<Listing<Article>>()

    val adapter = TopHeadLinesAdapter{retry()}

    val news = _result.switchMap { it.pagedList }
    val networkState = _result.switchMap { it.networkStatus }
    val observeNetworkStatus : Observer<NetworkStatus> = Observer { adapter.setNetworkState(it) }
    val refreshState = _result.switchMap { it.refreshStatus.map {networkStatus ->  networkStatus is NetworkStatus.Loading } }


    fun load(category: String, country: String){
        viewModelScope.launch {
            val param = Param(category = category, country = country, scope = viewModelScope)
            repo.work(param){ _result.postValue(it) }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            _result.value?.refresh?.invoke()
        }
    }

    private fun retry(){
        viewModelScope.launch {
            _result.value?.retry?.invoke()
        }
    }


}