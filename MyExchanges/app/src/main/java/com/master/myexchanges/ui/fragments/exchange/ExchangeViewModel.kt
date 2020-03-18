package com.master.myexchanges.ui.fragments.exchange

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.R
import com.master.myexchanges.data.repositories.interfaces.ICountryCurrencyRepository
import com.master.myexchanges.data.repositories.interfaces.ISearchRepository
import com.master.myexchanges.domain.CountryCurrency
import com.master.myexchanges.domain.Search
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseViewModel
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ExchangeViewModel(
    private val app: Application,
    private val searchRepository: ISearchRepository,
    private val countryCurrencyRepository: ICountryCurrencyRepository
) :
    BaseViewModel(app) {
    val sourceValue: MutableLiveData<Double?> by lazy {
        MutableLiveData<Double?>()
    }
    val sourceCurrencyPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val destinationValue: MutableLiveData<Double?> by lazy {
        MutableLiveData<Double?>()
    }
    val destinationCurrencyPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val currencyList: MutableLiveData<List<CountryCurrency>> by lazy {
        MutableLiveData<List<CountryCurrency>>()
    }
    val exchangeButtonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val exchangeError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val fatalError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }

    fun setSourceValue(valueText: String) {
        sourceValue.value = try {
            valueText.toDouble()
        } catch (e: Exception) {
            Log.d(app.packageName, e.message, e)
            null
        }
        validateExchangeButtonState()
    }

    fun setSourceCurrency(currency: Int) {
        sourceCurrencyPosition.value = currency
        validateExchangeButtonState()
    }

    fun setDestinationCurrency(currency: Int) {
        destinationCurrencyPosition.value = currency
        validateExchangeButtonState()
    }

    private fun validateExchangeButtonState() {
        exchangeButtonEnabled.value = validateData()
    }

    private fun validateData() =
        (sourceCurrencyPosition.value?.let {
            it >= 0
        } == true) && (destinationCurrencyPosition.value?.let {
            it >= 0
        } == true) && (sourceValue.value != null)

    fun doExchange(user: User?) {
        if (user == null) {
            fatalError.value = app.getString(R.string.error_on_get_user_data)
            return
        }
        if (validateData()) {
            exchangeButtonEnabled.value = false
            doWork(
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val valueToExchange = sourceValue.value!!
                        val sourceCurrency = currencyList.value!![sourceCurrencyPosition.value!!]
                        val destinationCurrency =
                            currencyList.value!![destinationCurrencyPosition.value!!]
                        val result =
                            searchRepository.searchExchage(sourceCurrency, destinationCurrency)
                        if (result.isSuccess) {
                            result.getOrNull()?.let { exchange ->
                                val totalExchanged = exchange.rate * valueToExchange
                                saveSearch(
                                    user,
                                    exchange.date, valueToExchange,
                                    sourceCurrency, totalExchanged, destinationCurrency
                                )
                                withContext(Dispatchers.Main) {
                                    destinationValue.value = totalExchanged
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                exchangeError.value = result.failureMessage()
                            }
                        }
                    }
                    finally {
                        withContext(Dispatchers.Main) {
                            exchangeButtonEnabled.value = true
                        }
                    }
                }
            )

        }
    }

    private suspend fun saveSearch(user : User,
        date: Date?, valueToExchange: Double, sourceCurrency: CountryCurrency,
        totalExchanged: Double, destinationCurrency: CountryCurrency
    ) {
        val search = Search(
            user, date, valueToExchange,
            sourceCurrency, totalExchanged, destinationCurrency
        )
        searchRepository.saveSearch(search)
    }

    fun getCountryCurrencyList() {
        doWork(
            viewModelScope.launch(Dispatchers.IO)
            {
                val result = countryCurrencyRepository.getAll()
                withContext(Dispatchers.Main)
                {
                    if (result.isSuccess) {
                        currencyList.value = result.getOrNull()
                    } else {
                        fatalError.value = result.failureMessage()
                    }
                }
            }
        )
    }
}
