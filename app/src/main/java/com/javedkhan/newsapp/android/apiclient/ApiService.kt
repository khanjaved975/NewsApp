package com.javedkhan.newsapp.android.apiclient

import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
 interface ApiService {

    @GET("svc/mostpopular/v2/viewed/7.json")
       fun getMostPopularArticals(
        @Query("api-key") access_key: String,
    ): Call<MostPopularViewResponse>


}
