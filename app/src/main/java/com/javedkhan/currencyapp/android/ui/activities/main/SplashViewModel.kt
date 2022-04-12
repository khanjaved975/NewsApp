package com.javedkhan.currencyapp.android.ui.activities.main

import android.os.Handler
import com.javedkhan.currencyapp.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashNavigator>() {
    //lateinit var handler: Handler
   /* fun startSeeding() {
        handler = Handler()
        handler.postDelayed({ decideNextActivity() }, SPLASH_TIME_OUT.toLong())
    }


    private fun decideNextActivity() {
        getNavigator()!!.decideNextActivity()
    }

    companion object {
        private val SPLASH_TIME_OUT = 2000
    }*/
}