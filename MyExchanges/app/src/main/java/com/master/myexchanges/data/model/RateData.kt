package com.master.myexchanges.data.model

import com.google.gson.annotations.SerializedName

data class RateData(
    @SerializedName("CAD")
    val cad: Double?,
    @SerializedName("HKD")
    val hkd: Double?,
    @SerializedName("ISK")
    val isk: Double?,
    @SerializedName("PHP")
    val php: Double?,
    @SerializedName("DKK")
    val dkk: Double?,
    @SerializedName("HUF")
    val huf: Double?,
    @SerializedName("CZK")
    val czk: Double?,
    @SerializedName("AUD")
    val aud: Double?,
    @SerializedName("RON")
    val ron: Double?,
    @SerializedName("SEK")
    val sek: Double?,
    @SerializedName("IDR")
    val idr: Double?,
    @SerializedName("INR")
    val inr: Double?,
    @SerializedName("BRL")
    val brl: Double?,
    @SerializedName("RUB")
    val rub: Double?,
    @SerializedName("HRK")
    val hrk: Double?,
    @SerializedName("JPY")
    val jpy: Double?,
    @SerializedName("THB")
    val thb: Double?,
    @SerializedName("CHF")
    val chf: Double?,
    @SerializedName("SGD")
    val sgd: Double?,
    @SerializedName("PLN")
    val pln: Double?,
    @SerializedName("BGN")
    val bgn: Double?,
    @SerializedName("TRY")
    val tri: Double?,
    @SerializedName("CNY")
    val cny: Double?,
    @SerializedName("NOK")
    val nok: Double?,
    @SerializedName("NZD")
    val nzd: Double?,
    @SerializedName("ZAR")
    val zar: Double?,
    @SerializedName("USD")
    val usd: Double?,
    @SerializedName("MXN")
    val mxn: Double?,
    @SerializedName("ILS")
    val ils: Double?,
    @SerializedName("GBP")
    val gbp: Double?,
    @SerializedName("KRW")
    val krw: Double?,
    @SerializedName("MYR")
    val myr: Double?
)
fun RateData.getRate(): Double {
    this.cad?.let {
        return it
    }
    this.hkd?.let {
        return it
    }
    this.isk?.let {
        return it
    }
    this.php?.let {
        return it
    }
    this.dkk?.let {
        return it
    }
    this.huf?.let {
        return it
    }
    this.czk?.let {
        return it
    }
    this.aud?.let {
        return it
    }
    this.ron?.let {
        return it
    }
    this.sek?.let {
        return it
    }
    this.idr?.let {
        return it
    }
    this.inr?.let {
        return it
    }
    this.brl?.let {
        return it
    }
    this.rub?.let {
        return it
    }
    this.hrk?.let {
        return it
    }
    this.jpy?.let {
        return it
    }
    this.thb?.let {
        return it
    }
    this.chf?.let {
        return it
    }
    this.sgd?.let {
        return it
    }
    this.pln?.let {
        return it
    }
    this.bgn?.let {
        return it
    }
    this.tri?.let {
        return it
    }
    this.cny?.let {
        return it
    }
    this.nok?.let {
        return it
    }
    this.nzd?.let {
        return it
    }
    this.zar?.let {
        return it
    }
    this.usd?.let {
        return it
    }
    this.mxn?.let {
        return it
    }
    this.ils?.let {
        return it
    }
    this.gbp?.let {
        return it
    }
    this.krw?.let {
        return it
    }
    this.myr?.let {
        return it
    }
    return 0.0
}