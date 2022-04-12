package com.javedkhan.currencyapp.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.javedkhan.currencyapp.android.di.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    private val isNetworkConnected: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }


    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
        appContext = applicationContext

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        var instance: MyApplication? = null
            private set

        var appContext: Context? = null
            private set

        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }

        operator fun get(activity: Activity): MyApplication {
            return activity.application as MyApplication
        }
    }


}
