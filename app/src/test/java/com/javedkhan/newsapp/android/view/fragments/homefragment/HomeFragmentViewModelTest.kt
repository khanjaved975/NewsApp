package com.javedkhan.newsapp.android.view.fragments.homefragment

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.utils.Constant
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.initMocks

class HomeFragmentViewModelTest {
    @Mock
    lateinit var dataRequest: ApiService
    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private val mutatableCurrencyResponse = MutableLiveData<CurrencyResponse>()

    @Before
    fun setUp() {
        initMocks(this)
        homeFragmentViewModel = HomeFragmentViewModel(dataRequest)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRates() {
        var gson = Gson()
        val currencyData=gson.fromJson(Constant.CurrencyStringData, CurrencyResponse::class.java)
        mutatableCurrencyResponse.postValue(currencyData)
        Mockito.`when`(dataRequest.getCurrencyRates(BuildConfig.API_KEY,"USD")).thenAnswer {
            assertEquals(currencyData, it)
        }

    }
}