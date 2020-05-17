package com.zedan.newsapp.ui.home.tophead

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zedan.newsapp.R
import com.zedan.newsapp.data.entities.ResponseTopHeadLines
import com.zedan.newsapp.databinding.TopHeadLinesItemInBinding

class TopHeadLinesItemViewHolder(
    private val binding: TopHeadLinesItemInBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): TopHeadLinesItemViewHolder {
            return TopHeadLinesItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.top_head_lines_item_in,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(item : ResponseTopHeadLines.Article?){
        binding.data = item
        binding.executePendingBindings()
    }
}