package com.master.myexchanges.ui.fragments.history

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.R
import com.master.myexchanges.data.repositories.interfaces.ISearchRepository
import com.master.myexchanges.domain.Search
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseViewModel
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val app: Application, private val repository: ISearchRepository) :
    BaseViewModel(app) {

    val searchList: MutableLiveData<List<Search>> by lazy {
        MutableLiveData<List<Search>>()
    }

    val fatalError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }

    val minorError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }

    fun loadUserSearchs(user: User?) {
        if (user == null) {
            fatalError.value = app.getString(R.string.error_on_get_user_data)
            return
        }
        doWork(
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getUserSearch(user)
                withContext(Dispatchers.Main) {
                    if (result.isSuccess) {
                        searchList.value = result.getOrNull()
                    } else {
                        minorError.value = result.failureMessage()
                    }
                }
            }
        )
    }
}