package com.javedkhan.newsapp.android.repository

import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import com.javedkhan.newsapp.android.utils.Resource
import java.lang.Exception

class DefaultRepository(private val apiHelper: ApiService) {
    suspend fun getArticleData(apikey:String): Resource<MostPopularViewResponse> {
        return try {
            val response = apiHelper.getMostPopularArticles(apikey)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}

