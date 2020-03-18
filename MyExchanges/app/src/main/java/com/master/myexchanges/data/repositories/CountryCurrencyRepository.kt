package com.master.myexchanges.data.repositories

import android.app.Application
import android.util.Log
import com.master.myexchanges.R
import com.master.myexchanges.data.database.dao.CountryCurrencyDao
import com.master.myexchanges.data.model.toCountryCurrencyList
import com.master.myexchanges.data.repositories.interfaces.ICountryCurrencyRepository
import com.master.myexchanges.domain.CountryCurrency
import java.lang.Exception
import com.master.myexchanges.util.Result

class CountryCurrencyRepository(val app: Application,val dao: CountryCurrencyDao): ICountryCurrencyRepository {
    override suspend fun getAll(): Result<List<CountryCurrency>> {
        return try {
            val result = dao.getAll()
            Result.success(result.toCountryCurrencyList())
        }catch (e: Exception)
        {
            Log.d("getAllCountryCurrency", e.message, e)
            Result.failure<List<CountryCurrency>>(app.getString(R.string.error_on_registering))
        }
    }

}