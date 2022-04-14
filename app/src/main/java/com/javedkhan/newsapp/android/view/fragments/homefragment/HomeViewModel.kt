package com.javedkhan.newsapp.android.view.fragments.homefragment

import androidx.lifecycle.viewModelScope
import com.javedkhan.newsapp.android.apiclient.ApiService
import com.javedkhan.newsapp.android.models.MostPopularViewResponse
import com.javedkhan.newsapp.android.repository.DefaultRepository
import com.javedkhan.newsapp.android.utils.Resource
import com.javedkhan.newsapp.android.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(apiService: ApiService) :
    BaseViewModel<HomeNavigator>() {
    private val mainRepository: DefaultRepository = DefaultRepository(apiService)

    sealed class ArticleEvent {
        class Success(val resultText: Resource<MostPopularViewResponse>) : ArticleEvent()
        class Failure(val errorText: String) : ArticleEvent()
        object Loading : ArticleEvent()
        object Empty : ArticleEvent()
    }

    private val _articleFlow = MutableStateFlow<ArticleEvent>(ArticleEvent.Empty)
    val articleDataFlow: StateFlow<ArticleEvent> = _articleFlow

    fun getArticles(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleFlow.value = ArticleEvent.Loading
            when (val responseData = mainRepository.getArticleData(apiKey)) {
                is Resource.Error -> _articleFlow.value = ArticleEvent.Failure(responseData.message.toString())
                is Resource.Success -> {
                    _articleFlow.value = ArticleEvent.Success(responseData)
                }
            }

        }
    }

}