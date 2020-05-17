package com.zedan.newsapp.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

/**
 * Use for Language functions to set Language or get it.
 */
object LanguageUtil {

    /**
     * Update Language
     * @param context for pass it to Shared Preferences to set Language
     */
    fun setLocale(context: Context) : Context{
        return setNewLocale(context, Preference.getLanguage(context))
    }

    fun setNewLocale(context: Context, lang: String) : Context{
        Preference.insertLanguage(context, lang)
        return updateResources(context, lang)
    }

    private fun updateResources(_context: Context, lang: String) : Context {
        var context = _context

        val locale = Locale(lang)

        Locale.setDefault(locale)

        val res : Resources = context.resources

        val config = Configuration(res.configuration)

        config.setLocale(locale)

        context = context.createConfigurationContext(config)

        return context
    }

}