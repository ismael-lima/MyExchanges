package com.master.myexchanges.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.master.myexchanges.domain.CountryCurrency

@Entity
data class CountryCurrencyData(
    @PrimaryKey val code: String,
    @ColumnInfo val name: String
)

fun CountryCurrencyData.toCountryCurrency() =
    CountryCurrency(this.code, this.name)

fun List<CountryCurrencyData>.toCountryCurrencyList() =
    this.map { c ->  c.toCountryCurrency() }