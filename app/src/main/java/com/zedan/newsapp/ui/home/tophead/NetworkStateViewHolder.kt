package com.zedan.newsapp.ui.home.tophead

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zedan.newsapp.R
import com.zedan.newsapp.binding.visible
import com.zedan.newsapp.databinding.NetworkStateItemBinding
import com.zedan.newsapp.domain.entities.NetworkStatus

class NetworkStateViewHolder(
    private val retryCallBack : ()->Unit,
    private val binding: NetworkStateItemBinding
) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.networkStateRetryButton.setOnClickListener { retryCallBack.invoke() }
    }

    companion object{
        fun create(parent: ViewGroup, retryCallBack: () -> Unit) : NetworkStateViewHolder{
            return NetworkStateViewHolder(retryCallBack,
                DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.network_state_item,
                        parent,
                        false
                    )
            )
        }
    }

    fun bind(networkState : NetworkStatus?){
        if (networkState is NetworkStatus.Error){
            binding.networkStateRetryButton.visible(true)
            binding.networkStateErrorMessage.visible(networkState.msg != null)
            binding.networkStateErrorMessage.text = networkState.msg
        }else{
            binding.networkStateRetryButton.visible(false)
            binding.networkStateErrorMessage.visible(false)
        }
        binding.networkStateProgressBar.visible(networkState is NetworkStatus.Loading)
    }
}