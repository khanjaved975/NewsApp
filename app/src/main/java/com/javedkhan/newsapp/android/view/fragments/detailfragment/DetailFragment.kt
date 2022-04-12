package com.javedkhan.newsapp.android.view.fragments.detailfragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.javedkhan.newsapp.android.BuildConfig
import com.javedkhan.newsapp.android.MyApplication
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.models.CurrencyHistory
import com.javedkhan.newsapp.android.databinding.FragmentDetailBinding
import com.javedkhan.newsapp.android.view.adapter.AdapterCurrencyList
import com.javedkhan.newsapp.android.view.adapter.AdapterList
import com.javedkhan.newsapp.android.view.base.BaseFragment
import com.javedkhan.newsapp.android.utils.Constant
import com.javedkhan.newsapp.android.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentViewModel>(),
    DetailNavigator {

    override val viewModel: DetailFragmentViewModel by viewModels()
    override val bindingVariable: Int get() = com.javedkhan.newsapp.android.BR.viewModel
    override val layoutId: Int get() = R.layout.fragment_detail
    private lateinit var fragmentBinding: FragmentDetailBinding
    lateinit var currencyHistoryData: CurrencyHistory
    var position: Int = 0

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
        fragmentBinding.toolbarTitle.text = "Currency Details"
        setDummyData()
    }


    fun apiCall() {
        try {
            if (MyApplication.hasNetwork()) {
                showProgressLoading("")
                viewModel.getCurrencyHistoryRates(
                    BuildConfig.API_KEY,
                    "2022-04-07", "2022-04-09"
                ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    hideProgressDialog()
                    if (it != null) {
                        if (it.success) {
                            setDummyData()
                            Toast.makeText(context,it.error.info,Toast.LENGTH_SHORT).show()
                        } else {
                            setDummyData()
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
        } catch (e: Exception) {
            e.localizedMessage
            hideProgressDialog()
            setDummyData()
        }
    }

    fun setDummyData() {
        try {
            var gson = Gson()
            currencyHistoryData = gson.fromJson(Constant.CurrencyHistoryData, CurrencyHistory::class.java)
            initBarChart()
            drawBarChart()
            setHistoricalList()
            setOtherCurrencies()
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    private fun initBarChart() {
//        hide grid lines
        fragmentBinding.barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = fragmentBinding.barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)

        //remove right y-axis
        fragmentBinding.barChart.axisRight.isEnabled = false

        //remove legend
        fragmentBinding.barChart.legend.isEnabled = true


        //remove description label
        fragmentBinding.barChart.description.isEnabled = true

        //add animation
        fragmentBinding.barChart.animateY(600)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    fun drawBarChart() {
        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        val scoreList = setScoreList(currencyHistoryData)
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.rate.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Historical Data")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(barDataSet)
        fragmentBinding.barChart.data = data
        fragmentBinding.barChart.invalidate()
    }

    private fun setHistoricalList() {
        var dateList: ArrayList<String> = ArrayList()
        val startdate: Date = java.sql.Date.valueOf(currencyHistoryData.startDate)

        for (i in 0 until currencyHistoryData.rates.size) {
            currencyHistoryData.startDate
            val date = DateUtils.addDay(startdate, i)
            val dateFormat = SimpleDateFormat("yyyy-MMM-dd")
            dateList.add(dateFormat.format(date))
        }
        val adapter =
            context?.let { it1 ->
                AdapterList(
                    it1,
                    dateList,
                    ::onClickItem
                )
            }
        fragmentBinding.rvHistorical.adapter = adapter

    }

    private fun setOtherCurrencies() {
        var otherCurrencyList: ArrayList<String> = ArrayList()
        otherCurrencyList.addAll(resources.getStringArray(R.array.currency_codes).toList())

        val otherCurrencyAdapter =
            context?.let { it1 ->
                AdapterCurrencyList(
                    it1,
                    otherCurrencyList,
                )
            }
        fragmentBinding.rvOtherCurrency.adapter = otherCurrencyAdapter
    }

    fun onClickItem(pos: Int) {
        this.position = pos
        drawBarChart()
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            val scoreList = setScoreList(currencyHistoryData)
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    data class Score(
        val name: String,
        val rate: Double,
    )

    private fun setScoreList(data: CurrencyHistory): ArrayList<Score> {
        var scoreList: ArrayList<Score> = ArrayList()
        scoreList.add(Score("USD", data.rates[position].uSD))
        scoreList.add(Score("AUD", data.rates[position].aUD))
        scoreList.add(Score("CAD", data.rates[position].cAD))
        return scoreList
    }

    override fun backPress() {
        navController.popBackStack()
    }


}