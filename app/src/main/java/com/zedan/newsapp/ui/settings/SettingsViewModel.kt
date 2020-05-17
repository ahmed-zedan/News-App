package com.zedan.newsapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.zedan.newsapp.util.SingleLiveEvent

class SettingsViewModel : ViewModel() {
    private val _navigation = SingleLiveEvent<NavDirections>()
    val navigation : LiveData<NavDirections> get() = _navigation

    fun languageClick(){
        _navigation.value = SettingsFragmentDirections.actionSettingsFragmentToLanguageBottomSheet()
    }
}
