package com.javedkhan.newsapp.android.repository

import androidx.lifecycle.MutableLiveData
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.apiclient.retrofit.RetrofitGenericResponse
import com.javedkhan.newsapp.android.apiclient.retrofit.RetrofitResponseCallback
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import retrofit2.Response
import java.lang.Exception

class DefaultRepository(val apiService: ApiService){

    fun getPopularNews(apiKey: String): MutableLiveData<MostPopularViewResponse?> {
        val data = MutableLiveData<MostPopularViewResponse?>()
        try {
            RetrofitGenericResponse.callRetrofit(apiService.getMostPopularArticals(apiKey), object :
                RetrofitResponseCallback {
                override fun success(response: Response<*>) {
                    if (response.body() != null) {
                        data.value = response.body() as MostPopularViewResponse
                    }
                }

                override fun error(error: Response<*>) {
                    data.value = null
                }

                override fun failure(message: String) {
                    data.value = null
                }
            })
        }catch (e:Exception){
            e.localizedMessage
        }


        return data

    }


}

