package com.master.myexchanges.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.data.repositories.interfaces.IUserRepository
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseViewModel
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application, private val repository: IUserRepository): BaseViewModel(app){

    val signOut: SingleLiveEvent<Unit> by lazy {
        SingleLiveEvent<Unit>()
    }
    val user: MutableLiveData<User> by lazy {
        SingleLiveEvent<User>()
    }

    fun logout() {
        doWork(
            viewModelScope.launch(Dispatchers.IO) {
                user.value?.let {
                    repository.logout(it)

                    withContext(Dispatchers.Main) {
                        signOut.call()
                    }
                }
            }
        )
    }
}