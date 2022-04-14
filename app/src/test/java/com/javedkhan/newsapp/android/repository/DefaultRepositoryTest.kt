package com.javedkhan.newsapp.android.repository

import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.base.BaseUTTest
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.MockKAnnotations
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class DefaultRepositoryTest :BaseUTTest(){
    //Target
    private lateinit var mRepo: DefaultRepository
    //Inject api service created with koin
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Mock
    lateinit var apiService: ApiService

    @Before
    override fun setUp() {
        super.setUp()
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    suspend fun testPopularNews() {
        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        mRepo = DefaultRepository(apiService)
        val dataReceived = mRepo.getArticleData(BuildConfig.API_KEY)
        assertNotNull(dataReceived)
        assertEquals(dataReceived.data?.results?.size , 20)
    }


}