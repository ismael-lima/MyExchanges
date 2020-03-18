package com.master.myexchanges.ui.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.R
import com.master.myexchanges.data.repositories.interfaces.IUserRepository
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseViewModel
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignInViewModel(
    private val app: Application,
    private val repository: IUserRepository
) : BaseViewModel(app) {
    val usernameOrEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val usernameOrEmailError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val passwordError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val signButtonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val signInError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val signInSuccess: SingleLiveEvent<User> by lazy {
        SingleLiveEvent<User>()
    }

    init {
        usernameOrEmail.value = ""
        password.value = ""
        signButtonEnabled.value = false
    }

    fun setUsernameOrEmail(usernameOrEmail: String) {
        this.usernameOrEmail.value = usernameOrEmail
        isUsernameOrEmailValid()
        validateSignButtonState()
    }

    private fun isUsernameOrEmailValid(): Boolean =
        usernameOrEmail.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                usernameOrEmailError.value = ""
                true
            } else {
                usernameOrEmailError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    fun setPassword(password: String) {
        this.password.value = password
        isPasswordValid()
        validateSignButtonState()
    }

    private fun isPasswordValid(): Boolean =
        password.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                passwordError.value = ""
                true
            } else {
                passwordError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    private fun validateSignButtonState() {
        signButtonEnabled.value = isUsernameOrEmailValid() && isPasswordValid()
    }

    fun retrieveSignedUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getLoggedUser()
                if(result.isSuccess) {
                    result.getOrNull()?.let {
                        withContext(Dispatchers.Main) {
                            usernameOrEmail.value = it.username
                            password.value = it.password
                            signIn()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(app.packageName, e.message, e)
            }
        }
    }

    fun signIn() {
        val usernameOrEmail = this.usernameOrEmail.value
        val password = this.password.value
        if ((usernameOrEmail != null) && (password != null)) {
            signButtonEnabled.value = false
            doWork(
                viewModelScope.launch(Dispatchers.IO) {
                    delay(1000)
                    try {
                        val result = repository.login(usernameOrEmail, password)

                        withContext(Dispatchers.Main) {
                            if (result.isSuccess)
                                signInSuccess.value = result.getOrNull()
                            else
                                signInError.value = result.failureMessage()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            signButtonEnabled.value = true
                        }
                    }
                }
            )
        }
    }
}