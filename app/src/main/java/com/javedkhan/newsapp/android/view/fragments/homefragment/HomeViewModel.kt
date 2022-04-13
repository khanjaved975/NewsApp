package com.javedkhan.newsapp.android.view.fragments.homefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(apiService: ApiService) : BaseViewModel<HomeNavigator>()  {
    private val dataRepository: DefaultRepository

    private var currencyResponse = MutableLiveData<MostPopularViewResponse?>()

    init {
        dataRepository = DefaultRepository(apiService)
    }



    fun getPopularNews(apiKey: String): MutableLiveData<MostPopularViewResponse?> {
        viewModelScope.launch(Dispatchers.Main) {
            currencyResponse=dataRepository.getPopularNews(apiKey)
        }
        return currencyResponse
    }


}