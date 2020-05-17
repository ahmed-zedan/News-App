package com.zedan.newsapp.ui.settings.language

import android.app.Application
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.zedan.newsapp.R
import com.zedan.newsapp.util.Preference
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val _result = MutableLiveData<Result>()
    val result : LiveData<Result> get() = _result

    private val allLanguage = hashMapOf(
        Pair(R.id.language_sheet_ar, "ar"),
        Pair(R.id.language_sheet_en, "en")
    )

    val checkedId : LiveData<Int> = liveData {
        val language = getCurrentLanguage()
        val reverseMap = allLanguage.entries.associate { it.value to it.key }
        val code = reverseMap[language]?:0
        emit(code)
    }
    fun chooseLanguage(checkedId: Int){
        _result.value =
            if (checkedId == this.checkedId.value)
                Result.IsOldLanguage
            else
                Result.Success(allLanguage[checkedId]?:"en")
    }

    private fun getCurrentLanguage() =  Preference.getLanguage(getApplication())

    sealed class Result{
        class Success(val newLanguage : String) : Result()
        object IsOldLanguage : Result()
    }
}