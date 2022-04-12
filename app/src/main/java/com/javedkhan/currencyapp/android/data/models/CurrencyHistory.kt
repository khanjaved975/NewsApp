package com.javedkhan.currencyapp.android.data.models
import com.google.gson.annotations.SerializedName


data class CurrencyHistory(
    @SerializedName("base")
    var base: String,
    @SerializedName("end_date")
    var endDate: String,
    @SerializedName("rates")
    var rates: List<Rate>,
    @SerializedName("start_date")
    var startDate: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("timeseries")
    var timeseries: Boolean
)

data class Rate(
    @SerializedName("AUD")
    var aUD: Double,
    @SerializedName("CAD")
    var cAD: Double,
    @SerializedName("USD")
    var uSD: Double
)