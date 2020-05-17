package com.zedan.newsapp.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.zedan.newsapp.R
import com.zedan.newsapp.util.LanguageUtil
import java.util.*

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageUtil.setLocale(this.baseContext)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtil.setLocale(newBase!!))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (Build.VERSION.SDK_INT in 19..25) {
            //Use you logic to update overrideConfiguration locale
            //your own implementation here;
            val locale = Locale.getDefault()
            overrideConfiguration?.let {
                overrideConfiguration.setLocale(locale)
            }
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}