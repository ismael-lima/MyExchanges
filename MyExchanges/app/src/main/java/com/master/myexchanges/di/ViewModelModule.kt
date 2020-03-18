package com.master.myexchanges.di

import com.master.myexchanges.ui.fragments.exchange.ExchangeViewModel
import com.master.myexchanges.ui.fragments.history.HistoryViewModel
import com.master.myexchanges.ui.main.MainViewModel
import com.master.myexchanges.ui.main.SharedViewModel
import com.master.myexchanges.ui.register.RegisterViewModel
import com.master.myexchanges.ui.signin.SignInViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignInViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { SharedViewModel() }
    viewModel { ExchangeViewModel(get(), get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
}