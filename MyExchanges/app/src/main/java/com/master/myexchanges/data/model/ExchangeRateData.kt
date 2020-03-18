package com.master.myexchanges.data.model

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.master.myexchanges.domain.ExchangeRate
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

data class ExchangeRateData(
    @SerializedName("rates")
    val rate: RateData?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?
)

@SuppressLint("SimpleDateFormat")
fun ExchangeRateData.toExchangeRate(): ExchangeRate {
    val sp = SimpleDateFormat("yyyy-MM-dd")
    val date = this.date?.let{
        try {
            sp.parse(it)
        } catch (e: Exception) {
            Log.d("toExchangeRate", e.message, e)
            null
        }
    } ?: Calendar.getInstance().time

    val rate = this.rate?.getRate() ?: 0.0
    return ExchangeRate(rate, date)
}