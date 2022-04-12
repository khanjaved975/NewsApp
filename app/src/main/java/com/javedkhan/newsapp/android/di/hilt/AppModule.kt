package com.javedkhan.newsapp.android.di.hilt


import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.data.service.retrofit.createNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = createNetworkClient()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(dataRequest: ApiService) = DefaultRepository(dataRequest)

}