package com.zedan.newsapp.data.entities

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.zedan.newsapp.domain.entities.NetworkStatus

data class Listing<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList : LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val networkStatus : LiveData<NetworkStatus>,
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    val refreshStatus: LiveData<NetworkStatus>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
    // retries any failed requests.
    val retry: () -> Unit
)