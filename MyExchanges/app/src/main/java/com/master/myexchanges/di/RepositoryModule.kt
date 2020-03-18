package com.master.myexchanges.di

import com.master.myexchanges.data.repositories.CountryCurrencyRepository
import com.master.myexchanges.data.repositories.SearchRepository
import com.master.myexchanges.data.repositories.UserRepository
import com.master.myexchanges.data.repositories.interfaces.ICountryCurrencyRepository
import com.master.myexchanges.data.repositories.interfaces.ISearchRepository
import com.master.myexchanges.data.repositories.interfaces.IUserRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =  module {
    factory { UserRepository(get(), get()) } bind IUserRepository::class
    factory { SearchRepository(get(), get(), get()) } bind ISearchRepository::class
    factory { CountryCurrencyRepository(get(), get()) } bind ICountryCurrencyRepository::class
}