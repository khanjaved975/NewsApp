package com.javedkhan.newsapp.android.view.fragments.detailfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.models.CurrencyHistoryResponse
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor (dataRequest: ApiService) : BaseViewModel<DetailNavigator>()  {
    private val defaultRepository: DefaultRepository

    private var currencyHistory = MutableLiveData<CurrencyHistoryResponse?>()

    init {
        defaultRepository = DefaultRepository(dataRequest)
    }

    fun getCurrencyHistoryRates(apiKey: String,starDate:String, endDate:String): MutableLiveData<CurrencyHistoryResponse?> {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                currencyHistory=defaultRepository.CurrencyHistoryData(apiKey,starDate,endDate)
            }
            currencyHistory
        }catch (e:Exception){
            e.localizedMessage
            currencyHistory
        }

    }
}