package com.javedkhan.currencyapp.android.ui.fragments.homefragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.javedkhan.currencyapp.android.BuildConfig
import com.javedkhan.currencyapp.android.MyApplication
import com.javedkhan.currencyapp.android.R
import com.javedkhan.currencyapp.android.databinding.FragmentHomeBinding
import com.javedkhan.currencyapp.android.ui.base.BaseFragment
import com.javedkhan.currencyapp.android.util.Constant.CurrencyStringData
import com.plcoding.currencyconverter.data.models.CurrencyResponse
import com.plcoding.currencyconverter.data.models.Rates
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.math.round

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(), HomeNavigator {

    override val bindingVariable: Int
        get() = com.javedkhan.currencyapp.android.BR.viewModel

    override val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var fragmentBinding: FragmentHomeBinding

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = this.viewDataBinding!!
        fragmentBinding.toolbarTitle.text = "Currency App"
        uiSetup()
    }

    fun uiSetup() {
        try {
            fragmentBinding.spFromCurrency.setSelection(2)
            fragmentBinding.spToCurrency.setSelection(3)
            fragmentBinding.spFromCurrency.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        resetResult()
                        fragmentBinding.etFrom.setText("")
                    }

                }

            fragmentBinding.spToCurrency.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        resetResult()
                        fragmentBinding.etFrom.setText("")

                    }

                }

            fragmentBinding.etFrom.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrEmpty()) {
                        resetResult()
                    } else {
                        s.toString().toFloatOrNull()?.let { apiCall(it) };
                    }
                }
            })

            fragmentBinding.btnDetail.setOnClickListener {
                DetailButtonClick()
            }

            fragmentBinding.btnSwap.setOnClickListener {
                SwapButtonClick()
            }
        }catch (e:Exception){
            e.localizedMessage
        }

    }

    fun apiCall(fromAmount: Float) {
        try {
            if (MyApplication.hasNetwork()) {
                viewModel.getRates(BuildConfig.API_KEY,
                    fragmentBinding.spFromCurrency.selectedItem.toString()).observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it.success) {
                            calculateRates(it,fromAmount)
                        } else {
                            setDummyData(fromAmount)
                            // activity?.let { it1 -> showAlert("Failed !", "Something went wrong", it1) }
                        }
                    }
                })
            } else {
                showAlert(
                    resources.getString(R.string.no_internet_title),
                    resources.getString(R.string.no_internet_msg),
                    requireActivity()
                )
            }
        }catch (e:Exception){
            e.localizedMessage
        }
    }

    private fun calculateRates(it: CurrencyResponse,fromAmount: Float){
        try {
            val rates = it.rates
            val rate = getRateForCurrency(
                fragmentBinding.spToCurrency.selectedItem.toString(),
                rates
            )
            if (rate != null) {
                val convertedCurrency = round((fromAmount!! * 100) * rate) / 100
                setResult(
                    fromAmount.toString(), convertedCurrency.toString(),
                    fragmentBinding.spFromCurrency.selectedItem.toString(),
                    fragmentBinding.spToCurrency.selectedItem.toString()
                )
            } else {
                activity?.let { it1 ->
                    showAlert(
                        "Error !",
                        "Something went wrong",
                        it1
                    )
                }
            }
        }catch (e:Exception){
            e.localizedMessage
        }

    }

    private fun setResult(
        fromAmount: String,
        toAmount: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        fragmentBinding.etTo.setText(toAmount)
        fragmentBinding.tvResult.text = "$fromAmount $fromCurrency = $toAmount $toCurrency"
    }

    fun resetResult() {
        fragmentBinding.etTo.setText("")
        fragmentBinding.tvResult.text = ""
    }

    fun setDummyData(fromAmount: Float){
        try {
            var gson = Gson()
            val data=gson.fromJson(CurrencyStringData, CurrencyResponse::class.java)
            calculateRates(data,fromAmount)
        }catch (e:Exception){
            e.localizedMessage
        }
    }

    override fun DetailButtonClick() {
        navController.navigate(R.id.action_homefragment_to_detailfragment)
    }

    override fun SwapButtonClick() {
        try {
            val toPosition=fragmentBinding.spToCurrency.selectedItemPosition
            val fromPosition=fragmentBinding.spFromCurrency.selectedItemPosition
            fragmentBinding.spFromCurrency.setSelection(toPosition)
            fragmentBinding.spToCurrency.setSelection(fromPosition)
            if(!fragmentBinding.etFrom.text.isNullOrEmpty()){
                var currentAmt=fragmentBinding.etFrom.text.toString().toFloat()
                apiCall(currentAmt)
                fragmentBinding.etFrom.setText(currentAmt.toString())
            }
        }catch (e: Exception){
            e.localizedMessage
        }

    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        else -> null
    }


}