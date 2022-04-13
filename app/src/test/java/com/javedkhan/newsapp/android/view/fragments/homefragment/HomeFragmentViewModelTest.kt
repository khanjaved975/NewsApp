package com.javedkhan.newsapp.android.view.fragments.homefragment

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.base.BaseUTTest
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.utils.Constant
import com.javedkhan.newsapp.android.utils.Resource
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock


@RunWith(JUnit4::class)
class HomeFragmentViewModelTest : BaseUTTest() {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var dataRequest: ApiService
    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    lateinit var  dataRepository: DefaultRepository

    lateinit var mutatableResponse :Resource<MostPopularViewResponse>

    @Before
    override fun setUp() {
        super.setUp()
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun getPopularNews() =  runBlocking<Unit>{
        homeFragmentViewModel = HomeFragmentViewModel(dataRequest)
        dataRepository= DefaultRepository(dataRequest)
        val sampleResponse = getJson("success_resp_list.json")
        var jsonObj = Gson().fromJson(sampleResponse, MostPopularViewResponse::class.java)
        mutatableResponse=dataRepository.getArticleData(BuildConfig.API_KEY)
        assert(mutatableResponse.data?.status  == Constant.OK)
        Assert.assertEquals(jsonObj, mutatableResponse.data)
    }

}