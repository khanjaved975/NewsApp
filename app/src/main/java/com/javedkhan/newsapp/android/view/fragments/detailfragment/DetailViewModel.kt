package com.javedkhan.newsapp.android.view.fragments.detailfragment

import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor (dataRequest: ApiService) : BaseViewModel<DetailNavigator>()  {
    private val defaultRepository: DefaultRepository = DefaultRepository(dataRequest)


}