package com.javedkhan.newsapp.android.view.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference


abstract class BaseViewModel<N> : ViewModel() {

    val isLoading = ObservableBoolean()

    private var mNavigator: WeakReference<N>? = null

    override fun onCleared() {
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getNavigator(): N? {
        return mNavigator?.get()
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

}
