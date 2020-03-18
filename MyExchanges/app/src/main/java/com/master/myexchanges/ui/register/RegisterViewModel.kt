package com.master.myexchanges.ui.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.master.myexchanges.R
import com.master.myexchanges.data.repositories.interfaces.IUserRepository
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseViewModel
import com.master.myexchanges.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val app: Application,
    private val repository: IUserRepository
) : BaseViewModel(app) {
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val nameError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val usernameError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val emailError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val passwordError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val confirmPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val confirmPasswordError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val registerButtonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val registerError: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }
    val registerSuccess: SingleLiveEvent<User> by lazy {
        SingleLiveEvent<User>()
    }

    init {
        name.value = ""
        username.value = ""
        email.value = ""
        password.value = ""
        confirmPassword.value = ""
        registerButtonEnabled.value = false
    }

    fun setName(name: String) {
        this.name.value = name
        isNameValid()
        validateRegisterButtonState()
    }

    private fun isNameValid(throwError: Boolean = true): Boolean =
        name.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                nameError.value = ""
                true
            } else {
                if (throwError)
                    nameError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    fun setUsername(username: String) {
        this.username.value = username
        isUsernameValid()
        validateRegisterButtonState()
    }

    private fun isUsernameValid(throwError: Boolean = true): Boolean =
        username.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                usernameError.value = ""
                true
            } else {
                if (throwError)
                    usernameError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    fun setEmail(email: String) {
        this.email.value = email
        isEmailValid()
        validateRegisterButtonState()
    }

    private fun isEmailValid(throwError: Boolean = true): Boolean =
        email.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                emailError.value = ""
                true
            } else {
                if (throwError)
                    emailError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    fun setPassword(password: String) {
        this.password.value = password
        isPasswordValid()
        validateRegisterButtonState()
    }

    private fun isPasswordValid(throwError: Boolean = true): Boolean =
        password.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                confirmPassword.value?.let { confirm ->
                    if (confirm.isNotBlank())
                        isConfirmPasswordValid()
                }
                passwordError.value = ""
                true
            } else {
                if (throwError)
                    passwordError.value = app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    fun setConfirmPassword(password: String) {
        this.confirmPassword.value = password
        isConfirmPasswordValid()
        validateRegisterButtonState()
    }

    private fun isConfirmPasswordValid(throwError: Boolean = true): Boolean =
        confirmPassword.value?.let {
            if (it.isNotEmpty() && it.isNotBlank()) {
                val password = password.value
                if (password?.isNotBlank() == true) {
                    if (password != it) {
                        if (throwError)
                            confirmPasswordError.value =
                                app.resources.getString(R.string.error_msg_password_dont_match)
                        false
                    } else {
                        confirmPasswordError.value = ""
                        true
                    }
                } else
                    false
            } else {
                if (throwError)
                    confirmPasswordError.value =
                        app.resources.getString(R.string.error_msg_blank_field)
                false
            }
        } ?: false

    private fun validateRegisterButtonState() {
        registerButtonEnabled.value =
            isNameValid(false) && isUsernameValid(false) && isEmailValid(false) && isPasswordValid(
                false
            ) && isConfirmPasswordValid(false)
    }

    fun register() {
        val name = this.name.value
        val username = this.username.value
        val email = this.email.value
        val password = this.password.value
        if ((name != null) && (username != null) && (email != null) && (password != null)) {
            registerButtonEnabled.value = false
            doWork(
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val result = repository.register(User("", name, username, email, password))

                        withContext(Dispatchers.Main) {
                            if (result.isSuccess)
                                registerSuccess.value = result.getOrNull()
                            else
                                registerError.value = result.failureMessage()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            registerButtonEnabled.value = true
                        }
                    }
                }
            )
        }
    }

}