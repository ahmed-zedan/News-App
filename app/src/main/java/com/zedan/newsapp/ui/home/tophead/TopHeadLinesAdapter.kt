package com.zedan.newsapp.ui.home.tophead

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zedan.newsapp.R
import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import com.zedan.newsapp.domain.entities.NetworkStatus

class TopHeadLinesAdapter(
    private val retryClick: ()->Unit
) : PagedListAdapter<ResponseTopHeadLines.Article, RecyclerView.ViewHolder>(DiffCallback){
    private var networkStatus : NetworkStatus? = null

    private val item  = R.layout.top_head_lines_item_in
    private val bottom = R.layout.network_state_item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            item -> (holder as TopHeadLinesItemViewHolder).bind(getItem(position))
            bottom -> (holder as NetworkStateViewHolder).bind(networkStatus)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType){
            item -> TopHeadLinesItemViewHolder.from(parent)
            bottom -> NetworkStateViewHolder.create(parent, retryClick)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkStatus != null && networkStatus !is NetworkStatus.Loading

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            bottom
        } else {
            item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkStatus?) {
        val previousState = this.networkStatus
        val hadExtraRow = hasExtraRow()
        this.networkStatus = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ResponseTopHeadLines.Article>(){
        override fun areItemsTheSame(
            oldItem: ResponseTopHeadLines.Article,
            newItem: ResponseTopHeadLines.Article
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: ResponseTopHeadLines.Article,
            newItem: ResponseTopHeadLines.Article
        ): Boolean {
            return oldItem == newItem
        }
    }
}