package com.javedkhan.newsapp.android.storage

import android.content.Context
import android.os.Build
import java.util.*

class SharedPreferencesManager(private val context: Context) {
    private val APP_INFO = "app_info"

    fun savePreferences(key: String, data: String) {
        val sharedPreferences = context.getSharedPreferences(APP_INFO, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
    }


    fun getPreferences(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(APP_INFO, Context.MODE_PRIVATE)
        return if (sharedPreferences != null) sharedPreferences.getString(key, "") else ""
    }

    fun savePreferences(key: String, data: Map<String, String>) {
        val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        for ((key1, value) in data) {
            editor.putString(key1, value)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
    }

    fun loadPreferences(key: String): Map<String, String> {
        val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        if (sharedPreferences != null && !sharedPreferences.all.isEmpty()) {
            val sharedPreferencesMap = sharedPreferences.all
            val map = HashMap<String, String>(sharedPreferencesMap.size)
            for ((key1, value) in sharedPreferencesMap) {
                if (key1 != null && value != null)
                    map[key1] = value.toString()
            }
            return map
        }
        return HashMap()
    }


    fun clearPreferences(key: String) {
        val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
    }


    fun saveBooleanPreferences(key: String, data: Boolean) {
        val sharedPreferences = context.getSharedPreferences(APP_INFO, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply()
        }
    }


    fun getBooleanPreferences(key: String): Boolean? {
        val sharedPreferences = context.getSharedPreferences(APP_INFO, Context.MODE_PRIVATE)
        return if (sharedPreferences != null) sharedPreferences.getBoolean(key, false) else false
    }


    fun clearAllPreferences(context: Context){
        val clearData = context.getSharedPreferences(APP_INFO, Context.MODE_PRIVATE)
        clearData.edit().clear().apply()
    }

}
