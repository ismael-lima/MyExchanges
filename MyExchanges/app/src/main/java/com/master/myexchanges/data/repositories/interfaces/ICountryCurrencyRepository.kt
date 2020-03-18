package com.master.myexchanges.data.repositories.interfaces

import com.master.myexchanges.domain.CountryCurrency
import com.master.myexchanges.util.Result

interface ICountryCurrencyRepository {
    suspend fun getAll(): Result<List<CountryCurrency>>
}