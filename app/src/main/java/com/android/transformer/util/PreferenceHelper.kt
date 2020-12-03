package com.android.transformer.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.android.transformer.TransformerApplication

/**
 * Helper class to access Shared Preferance
 */
object PreferenceHelper {
    var preferences: SharedPreferences? = null
    const val DEFAULT_INT_VALUE: Int = 0
    const val DEFAULT_STRING_VALUE: String = ""
    const val AUTH_TOKEN = "auth_token"

    init {
        preferences =
            PreferenceManager.getDefaultSharedPreferences(TransformerApplication.instance.applicationContext)
    }

    fun saveInt(key: String, value: Int) {
        preferences?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String): Int? {
        return preferences?.getInt(key, DEFAULT_INT_VALUE)
    }

    fun saveString(key: String, value: String) {
        preferences?.edit()?.putString(key, value)?.apply()
    }

    /**
     * Method to get the string from preferences
     * @param key key on which string saved
     */
    fun getString(key: String): String? {
        return preferences?.getString(key, DEFAULT_STRING_VALUE)
    }


}