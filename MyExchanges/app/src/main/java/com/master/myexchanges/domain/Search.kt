package com.master.myexchanges.domain

import com.master.myexchanges.data.model.SearchData
import java.util.*

class Search(
    val user: User,
    val date: Date?,
    val sourceValue : Double,
    val sourceCurrency: CountryCurrency,
    val destinationValue: Double,
    val destinationCurrency: CountryCurrency
)

fun Search.toSearchData() =
    SearchData(null,
        this.user.id,
        this.date,
        this.sourceCurrency.toCountryCurrencyData(),
        this.sourceValue,
        this.destinationCurrency.toCountryCurrencyData(),
        this.destinationValue
    )
