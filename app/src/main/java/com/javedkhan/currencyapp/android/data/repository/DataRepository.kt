package com.javedkhan.currencyapp.android.data.repository

import androidx.lifecycle.MutableLiveData
import com.javedkhan.currencyapp.android.data.service.retrofit.DataRequest
import com.javedkhan.currencyapp.android.data.models.CurrencyHistoryResponse
import com.javedkhan.currencyapp.android.data.services.genericresponse.RetrofitGenericResponse
import com.javedkhan.currencyapp.android.data.services.retrofit.RetrofitResponseCallback
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import java.lang.Exception

class DataRepository(val dataRequest: DataRequest) {

    fun CurrencyData(apiKey: String,base:String): MutableLiveData<CurrencyResponse?> {
        val data = MutableLiveData<CurrencyResponse?>()
        RetrofitGenericResponse.callRetrofit(dataRequest.getCurrencyRates(apiKey,base), object :
            RetrofitResponseCallback {
            override fun success(response: Response<*>) {
                if (response.body() != null) {
                    data.value = response.body() as CurrencyResponse
                }
            }

            override fun error(error: Response<*>) {
                data.value = null
            }

            override fun failure(message: String) {
                data.value = null
            }
        })

        return data

    }

    fun CurrencyHistoryData(apiKey: String,startdate:String,enddate:String): MutableLiveData<CurrencyHistoryResponse?> {
        val data = MutableLiveData<CurrencyHistoryResponse?>()
        try {
            RetrofitGenericResponse.callRetrofit(dataRequest.getCurrencyRatesInTimeFrame(apiKey,startdate,enddate), object :
                RetrofitResponseCallback {
                override fun success(response: Response<*>) {
                    if (response.body() != null) {
                        data.value = response.body() as CurrencyHistoryResponse
                    }
                }

                override fun error(error: Response<*>) {
                    data.value = null
                }

                override fun failure(message: String) {
                    data.value = null
                }
            })

            return data
        }catch (e:Exception){
            e.localizedMessage
            return data
        }
    }


}

