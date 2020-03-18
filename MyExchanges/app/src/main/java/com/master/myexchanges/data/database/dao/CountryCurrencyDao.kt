package com.master.myexchanges.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.master.myexchanges.data.model.CountryCurrencyData

@Dao
interface CountryCurrencyDao {
    @Query("SELECT * FROM CountryCurrencyData order by code")
    fun getAll(): List<CountryCurrencyData>
}
