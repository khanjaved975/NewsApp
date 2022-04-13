package com.javedkhan.newsapp.android.view.fragments.detailfragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.databinding.FragmentDetailBinding
import com.javedkhan.newsapp.android.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentViewModel>(),
    DetailNavigator {

    override val viewModel: DetailFragmentViewModel by viewModels()
    override val bindingVariable: Int get() = com.javedkhan.newsapp.android.BR.viewModel
    override val layoutId: Int get() = R.layout.fragment_detail
    private lateinit var fragmentBinding: FragmentDetailBinding
    lateinit var title: String
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = this.viewDataBinding!!
        fragmentBinding.ivToolbarBack.setOnClickListener {
            backPress()
        }
        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("title", "Article Details")
            url = bundle.getString("url", "")
        }
        fragmentBinding.toolbarTitle.text = title

        pageLoad()

    }

    private fun pageLoad() {
        if (MyApplication.hasNetwork()) {
            fragmentBinding.webView.webViewClient = activity?.let { MyWebViewClient(it) }!!
            fragmentBinding.webView.loadUrl(url)
        } else {
            showAlert(
                resources.getString(R.string.no_internet_title),
                resources.getString(R.string.no_internet_msg),
                requireActivity()
            )
        }
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            //Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }


    override fun backPress() {
        navController.popBackStack()
    }


}