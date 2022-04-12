package com.javedkhan.currencyapp.android.data.services.genericresponse

import android.util.Log
import com.javedkhan.currencyapp.android.util.CustomLogger.d
import com.javedkhan.currencyapp.android.data.services.retrofit.RetrofitResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

object RetrofitGenericResponse {
    private val TAG = RetrofitGenericResponse::class.java.simpleName
    fun <T> callRetrofit(call: Call<T>, retrofitResponseCallback: RetrofitResponseCallback) {
        try {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.raw().networkResponse != null) {
                        d(TAG, "onResponse: response is from NETWORK...")
                        if (response.isSuccessful) {
                            retrofitResponseCallback.success(response)
                        } else {
                            try {
                                d(TAG, "onResponse: " + response.errorBody()!!.string())
                                retrofitResponseCallback.error(response)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    } else if (response.raw().cacheResponse != null
                        && response.raw().networkResponse == null
                    ) {
                        d(TAG, "onResponse:response is from CACHE...")
                        if (response.isSuccessful) {
                            d(TAG, "onResponse: " + response.body().toString())
                            retrofitResponseCallback.success(response)
                        } else {
                            try {
                                d(TAG, "onResponse: " + response.errorBody()!!.string())
                                retrofitResponseCallback.error(response)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        retrofitResponseCallback.error(response)
                    }
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t")
                    retrofitResponseCallback.failure(t.toString())
                }
            })
        }catch (e:Exception){
            e.localizedMessage
        }

    }
}