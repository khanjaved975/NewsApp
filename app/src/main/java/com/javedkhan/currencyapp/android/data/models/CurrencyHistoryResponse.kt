package com.javedkhan.currencyapp.android.data.models
import com.google.gson.annotations.SerializedName


data class CurrencyHistoryResponse(
    var error: Error,
    var success: Boolean
)

data class Error(
    @SerializedName("code")
    var code: Int,
    @SerializedName("info")
    var info: String,
    @SerializedName("type")
    var type: String
)