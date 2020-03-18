package com.master.myexchanges.data.repositories.interfaces

import com.master.myexchanges.domain.CountryCurrency
import com.master.myexchanges.domain.ExchangeRate
import com.master.myexchanges.domain.Search
import com.master.myexchanges.domain.User
import com.master.myexchanges.util.Result

interface ISearchRepository {
    suspend fun searchExchage(base: CountryCurrency, destination: CountryCurrency):Result<ExchangeRate>
    suspend fun saveSearch(search: Search)
    suspend fun getUserSearch(user: User):Result<List<Search>>
}