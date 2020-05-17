package com.zedan.newsapp.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zedan.newsapp.ui.home.tophead.TopHeadLines

class AdapterFragment(
    fragment: Fragment,
    private val allFragment: List<Pair<Int, String>>
) :  FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = allFragment.size

    override fun createFragment(position: Int): Fragment {
        return TopHeadLines.getInstance(allFragment[position].second)
    }

}