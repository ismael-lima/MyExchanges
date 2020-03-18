package com.master.myexchanges.domain

import com.master.myexchanges.data.model.CountryCurrencyData

class CountryCurrency(
    val code: String,
    val name: String
){
    override fun toString(): String {
        return "$code ($name)"
    }
}

fun CountryCurrency.toCountryCurrencyData() =
    CountryCurrencyData(this.code, this.name)