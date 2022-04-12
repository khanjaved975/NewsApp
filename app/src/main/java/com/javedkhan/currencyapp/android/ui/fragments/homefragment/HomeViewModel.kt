package com.javedkhan.currencyapp.android.ui.fragments.homefragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javedkhan.currencyapp.android.data.repository.DataRepository
import com.javedkhan.currencyapp.android.data.service.retrofit.DataRequest
import com.javedkhan.currencyapp.android.ui.base.BaseViewModel
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(dataRequest: DataRequest) : BaseViewModel<HomeNavigator>()  {
    private val dataRepository: DataRepository

    private var currencyResponse = MutableLiveData<CurrencyResponse?>()

    init {
        dataRepository = DataRepository(dataRequest)
    }

    fun getRates(apiKey: String,base:String): MutableLiveData<CurrencyResponse?> {
        viewModelScope.launch(Dispatchers.IO) {
            currencyResponse=dataRepository.CurrencyData(apiKey,base)
        }
        return currencyResponse
    }


}