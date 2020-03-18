package com.master.myexchanges.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.master.myexchanges.data.api.ExchangeApi
import com.master.myexchanges.data.api.ExchangeService
import com.master.myexchanges.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module


val dataModule =  module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "appdatabase")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch(Dispatchers.IO){
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('USD', 'US dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('JPY', 'Japanese yen');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('BGN', 'Bulgarian lev');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('CZK', 'Czech koruna');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('DKK', 'Danish krone');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('GBP', 'Pound sterling');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('HUF', 'Hungarian forint');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('PLN', 'Polish zloty');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('RON', 'Romanian leu');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('SEK', 'Swedish krona');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('CHF', 'Swiss franc');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('ISK', 'Icelandic krona');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('NOK', 'Norwegian krone');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('HRK', 'Croatian kuna');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('RUB', 'Russian rouble');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('TRY', 'Turkish lira');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('AUD', 'Australian dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('BRL', 'Brazilian real');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('CAD', 'Canadian dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('CNY', 'Chinese yuan renminbi');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('HKD', 'Hong Kong dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('IDR', 'Indonesian rupiah');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('ILS', 'Israeli shekel');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('INR', 'Indian rupee');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('KRW', 'South Korean won');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('MXN', 'Mexican peso');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('MYR', 'Malaysian ringgit');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('NZD', 'New Zealand dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('PHP', 'Philippine peso');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('SGD', 'Singapore dollar');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('THB', 'Thai baht');")
                        db.execSQL("INSERT INTO CountryCurrencyData VALUES('ZAR', 'South African rand');")
                    }
                }
            }).build()
    }

    single {
        get<AppDatabase>().UserDao()
    }

    single {
        get<AppDatabase>().SearchDao()
    }

    single {
        get<AppDatabase>().CountryCurrencyDao()
    }

    single {
        ExchangeApi.createService(ExchangeService::class.java)
    }
}