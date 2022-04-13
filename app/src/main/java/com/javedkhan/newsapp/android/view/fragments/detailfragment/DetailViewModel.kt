package com.javedkhan.newsapp.android.view.fragments.detailfragment

import androidx.lifecycle.MutableLiveData
import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor (dataRequest: ApiService) : BaseViewModel<DetailNavigator>()  {
    private val defaultRepository: DefaultRepository

    init {
        defaultRepository = DefaultRepository(dataRequest)
    }


}