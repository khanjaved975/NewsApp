package com.javedkhan.newsapp.android.view.fragments.homefragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.databinding.FragmentHomeBinding
import com.javedkhan.newsapp.android.models.Result
import com.javedkhan.newsapp.android.view.adapter.AdapterList
import com.javedkhan.newsapp.android.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(), HomeNavigator {

    override val bindingVariable: Int
        get() = com.javedkhan.newsapp.android.BR.viewModel

    override val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var homeFragmentBinding: FragmentHomeBinding

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentBinding = this.viewDataBinding!!
        setUI()
    }

    private fun setUI() {
        homeFragmentBinding.toolbarTitle.text = resources.getString(R.string.nytimes)
        homeFragmentBinding.btnRefresh.setOnClickListener {
            getPopularArticle()
        }
        getPopularArticle()
    }

    private fun getPopularArticle() {
        try {
            if (MyApplication.hasNetwork()) {
                viewModel.getArticles(BuildConfig.API_KEY)
                lifecycleScope.launchWhenStarted {
                    viewModel.articleDataFlow.collect {
                        when (it) {
                            is HomeFragmentViewModel.ArticleEvent.Success -> {
                                hideProgressDialog()
                                homeFragmentBinding.btnRefresh.visibility=View.GONE
                                it.resultText.data?.let { it1 -> setArticleListView(it1.results) }
                            }
                            is HomeFragmentViewModel.ArticleEvent.Failure -> {
                                hideProgressDialog()
                                homeFragmentBinding.btnRefresh.visibility=View.VISIBLE
                                activity?.let { it1 -> showAlert(resources.getString(R.string.error)
                                    ,it.errorText, it1) }
                            }
                            is HomeFragmentViewModel.ArticleEvent.Loading -> {
                                showProgressLoading("")
                            }
                            else -> {
                                hideProgressDialog()
                            }
                        }
                    }
                }
            } else {
                showAlert(
                    resources.getString(R.string.no_internet_title),
                    resources.getString(R.string.no_internet_msg),
                    requireActivity()
                )
            }
        } catch (e: Exception) {
            showAlert(
                resources.getString(R.string.error),
                e.localizedMessage,
                requireActivity()
            )
        }
    }

    private fun setArticleListView(results: List<Result>) {
        val adapter =
            context?.let { it1 ->
                AdapterList(
                    it1,
                    results,
                    ::onClickItem
                )
            }
        homeFragmentBinding.rvArtical.adapter = adapter
    }

    private fun onClickItem(result: Result) {
        articleDetailPageNavigation(result.title, result.url)
    }

    override fun articleDetailPageNavigation(title: String, url: String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("url", url)
        navController.navigate(R.id.action_homefragment_to_detailfragment, bundle)
    }


}