package com.javedkhan.newsapp.android.view.fragments.homefragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.databinding.FragmentHomeBinding
import com.javedkhan.newsapp.android.models.Result
import com.javedkhan.newsapp.android.utils.Constant
import com.javedkhan.newsapp.android.view.base.BaseFragment
import com.javedkhan.newsapp.android.view.adapter.AdapterList
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

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

    override fun onStart() {
        super.onStart()
        getPopularArticle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentBinding = this.viewDataBinding!!
        homeFragmentBinding.toolbarTitle.text = resources.getString(R.string.nytimes)
        homeFragmentBinding.btnRefresh.setOnClickListener {
            getPopularArticle()
        }
    }

    override fun onResume() {
        super.onResume()
        getPopularArticle()
    }

    private fun getPopularArticle() {
        try {
            if (MyApplication.hasNetwork()) {
                viewModel.getPopularNews(BuildConfig.API_KEY).observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (it.status == Constant.OK) {
                            setNewsList(it.results)
                        } else {
                            activity?.let { it1 ->
                                showAlert(
                                    "Failed !",
                                    "Something went wrong",
                                    it1
                                )
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

    private fun setNewsList(results: List<Result>) {
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
       // Toast.makeText(context, result.title, Toast.LENGTH_SHORT).show()
        ArticleDetailPageNavigation(result.title,result.url)
    }

    override fun ArticleDetailPageNavigation(title: String,url:String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("url", url)
        navController.navigate(R.id.action_homefragment_to_detailfragment,bundle)
    }


}