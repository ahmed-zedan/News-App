package com.zedan.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.zedan.newsapp.R
import com.zedan.newsapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val listOfFragments : List<Pair<Int, String>> by lazy {
        listOf(
                Pair(R.string.general, "general"),
                Pair(R.string.health, "health"),
                Pair(R.string.sports, "sports"),
                Pair(R.string.technology, "technology"),
                Pair(R.string.business, "business"),
                Pair(R.string.entertainment, "entertainment"),
                Pair(R.string.science, "science")
        )
    }

    private val adapter: AdapterFragment by lazy { AdapterFragment(this, listOfFragments) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = home_view_pager
        val tabLayout = home_tab_layout
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.setText(listOfFragments[position].first)
        }.attach()

    }
}
