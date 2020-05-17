package com.zedan.newsapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.zedan.newsapp.R


object Preference {

    init {
        System.loadLibrary("native-lib")
    }

    fun insertLanguage(context: Context, language : String){
        secureSharedPref(context).edit {
            putString(getKeys(1), language)
        }
    }

    /**
     * get Language from shared preferences if not set language yet in shared preferences
     * get it from string.xml
     * @param context to get Shared Preferences and use it
     */
    fun getLanguage(context: Context): String {
        return secureSharedPref(context)
            .getString(
                getKeys(1),
                context.resources.getString(R.string.lang))!!
    }

    private fun secureSharedPref(context: Context) : SharedPreferences{
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences
            .create(
                sharedPrefName(),
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    private external fun sharedPrefName() : String
    private external fun getKeys(code : Int) : String

    private inline fun SharedPreferences.edit(data : SharedPreferences.Editor.()->Unit){
        val editor = edit()
        data.invoke(editor)
        editor.apply()
    }
}