package com.javedkhan.newsapp.android.data.service.retrofit

import com.readystatesoftware.chuck.ChuckInterceptor
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.utils.Constant.retrofitCacheFile
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit



val sLogLevel =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

// cache size mention here
val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB

fun cache(): Cache {
    return Cache(File(MyApplication.instance?.cacheDir, retrofitCacheFile), cacheSize)
}

fun getLogInterceptor() = HttpLoggingInterceptor().apply { level = sLogLevel }
        .setLevel(HttpLoggingInterceptor.Level.BODY)

fun createNetworkClient() =
        retrofitClient(okHttpClient())

private fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(getLogInterceptor())
        .addInterceptor(ChuckInterceptor(MyApplication.instance))
        .apply { setTimeOutToOkHttpClient(this) }
        .addInterceptor(headersInterceptor())
        .build()

private fun retrofitClient(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
        okHttpClientBuilder.apply {
            readTimeout(1L, TimeUnit.HOURS)
            connectTimeout(1L, TimeUnit.HOURS)
            writeTimeout(1L, TimeUnit.HOURS)
            retryOnConnectionFailure(true)
        }

fun headersInterceptor() = Interceptor { chain ->
    chain.proceed(
        chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
    )
}