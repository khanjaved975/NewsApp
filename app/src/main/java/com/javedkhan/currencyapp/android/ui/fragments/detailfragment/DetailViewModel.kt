package com.javedkhan.currencyapp.android.ui.fragments.detailfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javedkhan.currencyapp.android.data.repository.DataRepository
import com.javedkhan.currencyapp.android.data.models.CurrencyHistoryResponse
import com.javedkhan.currencyapp.android.data.service.retrofit.DataRequest
import com.javedkhan.currencyapp.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor (dataRequest: DataRequest) : BaseViewModel<DetailNavigator>()  {
    private val dataRepository: DataRepository

    private var currencyHistory = MutableLiveData<CurrencyHistoryResponse?>()

    init {
        dataRepository = DataRepository(dataRequest)
    }

    fun getCurrencyHistoryRates(apiKey: String,starDate:String, endDate:String): MutableLiveData<CurrencyHistoryResponse?> {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                currencyHistory=dataRepository.CurrencyHistoryData(apiKey,starDate,endDate)
            }
            currencyHistory
        }catch (e:Exception){
            e.localizedMessage
            currencyHistory
        }

    }
}