package com.master.myexchanges.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.master.myexchanges.data.database.dao.CountryCurrencyDao
import com.master.myexchanges.data.database.dao.SearchDao
import com.master.myexchanges.data.database.dao.UserDao
import com.master.myexchanges.data.model.CountryCurrencyData
import com.master.myexchanges.data.model.SearchData
import com.master.myexchanges.data.model.UserData
import com.master.myexchanges.util.Converters

@Database(
    entities = [UserData::class, SearchData::class, CountryCurrencyData::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun SearchDao(): SearchDao
    abstract fun CountryCurrencyDao(): CountryCurrencyDao
}