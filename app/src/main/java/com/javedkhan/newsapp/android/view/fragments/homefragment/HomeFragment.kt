package com.javedkhan.newsapp.android.view.fragments.homefragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.androidnetworking.utils.Utils
import com.google.gson.Gson
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.databinding.FragmentHomeBinding
import com.javedkhan.newsapp.android.utils.Constant
import com.javedkhan.newsapp.android.view.base.BaseFragment
import com.javedkhan.newsapp.android.utils.Constant.CurrencyStringData
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import com.plcoding.currencyconverter.data.models.Rates
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.math.round

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
        setUI()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentBinding = this.viewDataBinding!!
        homeFragmentBinding.toolbarTitle.text = "Currency App"
    }

    fun setUI() {
        try {
            homeFragmentBinding.spFromCurrency.setSelection(2)
            homeFragmentBinding.spToCurrency.setSelection(3)
            homeFragmentBinding.btnDetail.setOnClickListener {
                DetailButtonClick()
            }

            homeFragmentBinding.btnSwap.setOnClickListener {
                SwapButtonClick()
            }
        } catch (e: Exception) {
            e.localizedMessage
        }

    }

    fun getPopularArtical() {
        try {
            if (MyApplication.hasNetwork()) {
                showProgressLoading("")
                viewModel.getPopularNews(BuildConfig.API_KEY).observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        hideProgressDialog()
                        if (it.status == Constant.OK) {
                            activity?.let { it1 -> showAlert("Success !", it.copyright, it1) }
                        } else {
                             activity?.let { it1 -> showAlert("Failed !", "Something went wrong", it1) }
                        }
                    }
                })
            } else {
                hideProgressDialog()
                showAlert(
                    resources.getString(R.string.no_internet_title),
                    resources.getString(R.string.no_internet_msg),
                    requireActivity()
                )
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    override fun DetailButtonClick() {
        getPopularArtical()

        //navController.navigate(R.id.action_homefragment_to_detailfragment)
    }

    override fun SwapButtonClick() {
        try {
            val toPosition = homeFragmentBinding.spToCurrency.selectedItemPosition
            val fromPosition = homeFragmentBinding.spFromCurrency.selectedItemPosition
            homeFragmentBinding.spFromCurrency.setSelection(toPosition)
            homeFragmentBinding.spToCurrency.setSelection(fromPosition)
            if (!homeFragmentBinding.etFrom.text.isNullOrEmpty()) {
                var currentAmt = homeFragmentBinding.etFrom.text.toString().toFloat()
                //apiCall(currentAmt)
                homeFragmentBinding.etFrom.setText(currentAmt.toString())
            }
        } catch (e: Exception) {
            e.localizedMessage
        }

    }



    override fun onDetach() {
        super.onDetach()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}