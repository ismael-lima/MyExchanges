package com.master.myexchanges.data.repositories

import android.app.Application
import android.util.Log
import com.master.myexchanges.R
import com.master.myexchanges.data.api.ExchangeService
import com.master.myexchanges.data.database.dao.SearchDao
import com.master.myexchanges.data.model.toExchangeRate
import com.master.myexchanges.data.model.toSearchList
import com.master.myexchanges.data.repositories.interfaces.ISearchRepository
import com.master.myexchanges.domain.*
import com.master.myexchanges.util.Result

class SearchRepository(
    private val app: Application,
    private val dao: SearchDao,
    private val api: ExchangeService
) : ISearchRepository {
    override suspend fun searchExchage(base:CountryCurrency, destination: CountryCurrency):Result<ExchangeRate>{
        return try {
            val result = api.getExchangeRate(base.code, destination.code)
            Result.success(result.toExchangeRate())
        }catch (e: Exception)
        {
            Log.d(app.packageName, e.message, e)
            Result.failure<ExchangeRate>(app.getString(R.string.error_on_search_exchange))
        }
    }

    override suspend fun saveSearch(search: Search){
        dao.inserir(search.toSearchData())
    }

    override suspend fun getUserSearch(user: User):Result<List<Search>>{
        return try {
            val result = dao.getByUserId(user.id)
            Result.success(result.toSearchList(user))
        }catch (e: Exception)
        {
            Log.d(app.packageName, e.message, e)
            Result.failure<List<Search>>(app.getString(R.string.error_on_search_history))
        }
    }
}