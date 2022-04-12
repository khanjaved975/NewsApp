package com.javedkhan.currencyapp.android.di.hilt


import android.content.Context
import com.javedkhan.currencyapp.android.MyApplication.Companion.appContext
import com.javedkhan.currencyapp.android.data.manager.SharedPreferencesManager
import com.javedkhan.currencyapp.android.data.repository.DataRepository
import com.javedkhan.currencyapp.android.data.service.retrofit.DataRequest
import com.javedkhan.currencyapp.android.data.service.retrofit.createNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = createNetworkClient()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): DataRequest = retrofit.create(DataRequest::class.java)

    @Singleton
    @Provides
    fun providesRepository(dataRequest: DataRequest) = DataRepository(dataRequest)

}