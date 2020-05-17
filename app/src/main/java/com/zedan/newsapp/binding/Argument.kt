package com.zedan.newsapp.binding

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

fun ViewDataBinding.setViewModel(
    viewModel: ViewModel,
    lifecycleOwner: LifecycleOwner
){
    setVariable(BR.viewModel, viewModel)
    this.lifecycleOwner = lifecycleOwner
    executePendingBindings()
}