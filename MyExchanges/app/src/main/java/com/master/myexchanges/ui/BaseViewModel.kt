package com.master.myexchanges.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.*

open class BaseViewModel(aplicacao: Application) : AndroidViewModel(aplicacao) {
    private val startWorkEvent = SingleLiveEvent<Void>()
    private val finishWorkEvent = SingleLiveEvent<Void>()
    private val showErrorEvent = SingleLiveEvent<Exception>()

    protected fun doWork(
        job: Job
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main){
                    startWorkEvent.call()
                }
                job.join()
            } catch (e: Exception) {
                withContext(Dispatchers.IO){
                    Log.e(e.javaClass.name, e.message, e)
                    showErrorEvent.postValue(e)
                }
            } finally {
                withContext(Dispatchers.Main){
                    finishWorkEvent.call()
                }
            }
        }
    }

    fun observe(
        owner: LifecycleOwner,
        startWork: () -> Unit,
        finishWork: () -> Unit,
        showError: (Exception) -> Unit
    ) {
        startWorkEvent.observe(owner, startWork)
        finishWorkEvent.observe(owner, finishWork)
        showErrorEvent.observe(owner, Observer(showError))
    }
}