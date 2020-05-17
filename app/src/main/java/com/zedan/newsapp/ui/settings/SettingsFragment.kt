package com.zedan.newsapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zedan.newsapp.base.BaseFragment
import com.zedan.newsapp.binding.setViewModel
import com.zedan.newsapp.databinding.SettingsFragmentBinding

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return SettingsFragmentBinding.inflate(inflater)
            .apply {
                setViewModel(this@SettingsFragment.viewModel, this@SettingsFragment)
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.let { navigate(it) }
        })
    }

}
