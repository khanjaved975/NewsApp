package com.javedkhan.newsapp.android.apiclient

import com.javedkhan.newsapp.android.models.CurrencyHistoryResponse
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
 interface ApiService {
//http://data.fixer.io/api/latest?access_key=a5074dc3a40997e19f446c2416ef93a6
//http://data.fixer.io/api/timeseries?access_key=a5074dc3a40997e19f446c2416ef93a6&start_date=2022-03-22&end_date=2022-03-25

    @GET("svc/mostpopular/v2/viewed/7.json")
       fun getMostPopularArticals(
        @Query("api-key") access_key: String,
    ): Call<MostPopularViewResponse>

       @GET("api/latest")
       fun getCurrencyRates(
        @Query("access_key") access_key: String,
        @Query("base") base: String
    ): Call<CurrencyResponse>



}
