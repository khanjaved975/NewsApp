package com.javedkhan.currencyapp.android.data.service.retrofit

import com.javedkhan.currencyapp.android.BuildConfig
import com.javedkhan.currencyapp.android.data.models.CurrencyHistoryResponse
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
 interface DataRequest {
//http://data.fixer.io/api/latest?access_key=a5074dc3a40997e19f446c2416ef93a6
//http://data.fixer.io/api/timeseries?access_key=a5074dc3a40997e19f446c2416ef93a6&start_date=2022-03-22&end_date=2022-03-25

    @GET("api/latest")
     fun getCurrencyRates(
        @Query("access_key") access_key: String,
        @Query("base") base: String
    ): Call<CurrencyResponse>

    @GET("api/timeseries")
    fun getCurrencyRatesInTimeFrame(
        @Query("access_key") access_key: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<CurrencyHistoryResponse>

}
