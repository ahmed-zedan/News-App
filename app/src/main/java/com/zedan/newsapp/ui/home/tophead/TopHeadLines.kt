package com.zedan.newsapp.ui.home.tophead

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.zedan.newsapp.R
import com.zedan.newsapp.base.BaseFragment
import com.zedan.newsapp.binding.setViewModel
import com.zedan.newsapp.data.repository.TopHeadLinesRepository
import com.zedan.newsapp.databinding.TopHeadLinesFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class TopHeadLines : BaseFragment() {

    companion object{
        private const val KEY_CATEGORY = "category_key"
        fun getInstance(category: String) : TopHeadLines{
            val fragment = TopHeadLines()
            fragment.arguments = Bundle().apply { putString(KEY_CATEGORY, category) }
            return fragment
        }
    }

    private val viewModel : TopHeadLinesViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory(){
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TopHeadLinesViewModel(
                    repo = TopHeadLinesRepository()
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : TopHeadLinesFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.top_head_lines_fragment, container, false)
        binding.setViewModel(viewModel, this)
        observerNews(binding.topHeadLinesRecycler)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun observerNews(list: RecyclerView){
        val dividerItem = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        list.addItemDecoration(dividerItem)
        viewModel.news.observe(viewLifecycleOwner, Observer {
            viewModel.adapter.submitList(it){
                // Workaround for an issue where RecyclerView incorrectly uses the loading / spinner
                // item added to the end of the list as an anchor during initial load.
                val layoutManager = (list.layoutManager as LinearLayoutManager)
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    list.scrollToPosition(position)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(KEY_CATEGORY) }?.apply{
            val category = getString(KEY_CATEGORY, "general")
            viewModel.load(category, "eg")

        }
        viewModel.networkState.observe(viewLifecycleOwner, viewModel.observeNetworkStatus)
    }

}
