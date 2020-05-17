package com.zedan.newsapp.ui.settings.language

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zedan.newsapp.MainActivity
import com.zedan.newsapp.R
import com.zedan.newsapp.base.BottomSheet
import com.zedan.newsapp.binding.setViewModel
import com.zedan.newsapp.databinding.LanguageBottomSheetBinding
import com.zedan.newsapp.util.LanguageUtil

/**
 * A simple [Fragment] subclass.
 */
class LanguageBottomSheet : BottomSheet() {
    private val viewModel by viewModels<LanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LanguageBottomSheetBinding>(
            inflater,
            R.layout.language_bottom_sheet, container, false
        )
        binding.setViewModel(viewModel, this)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer {
            when(it){
                is LanguageViewModel.Result.Success->{
                    LanguageUtil.setNewLocale(requireContext(), it.newLanguage)
                    requireActivity().finish()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
                is LanguageViewModel.Result.IsOldLanguage ->{
                    showToast(R.string.this_is_your_language)
                }
            }
        })
    }

}
