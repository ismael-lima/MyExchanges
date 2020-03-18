package com.master.myexchanges

import android.app.Application
import com.master.myexchanges.di.dataModule
import com.master.myexchanges.di.repositoryModule
import com.master.myexchanges.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyExchangesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MyExchangesApplication)
            modules(
                listOf(
                    dataModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}