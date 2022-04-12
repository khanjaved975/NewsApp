package com.javedkhan.newsapp.android.repository

import androidx.lifecycle.MutableLiveData
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.models.CurrencyHistoryResponse
import com.javedkhan.newsapp.android.apiclient.retrofit.RetrofitGenericResponse
import com.javedkhan.newsapp.android.apiclient.retrofit.RetrofitResponseCallback
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import java.lang.Exception

class DefaultRepository(val apiService: ApiService){

    fun getCurrencyData(apiKey: String,base:String): MutableLiveData<CurrencyResponse?> {
        val data = MutableLiveData<CurrencyResponse?>()
        RetrofitGenericResponse.callRetrofit(apiService.getCurrencyRates(apiKey,base), object :
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
            RetrofitGenericResponse.callRetrofit(apiService.getCurrencyRatesInTimeFrame(apiKey,startdate,enddate), object :
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

