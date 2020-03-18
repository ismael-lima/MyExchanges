package com.master.myexchanges.util

class Result<out T> private constructor(val _value: Any?, val _success: Boolean) {
    val isSuccess: Boolean get() = _success

    fun getOrNull(): T? =
        when {
            isSuccess -> _value as T
            else -> null
        }

    fun failureMessage(): String? =
        when  {
            !_success -> _value.toString()
            else -> null
        }

    companion object {
        fun <T> success(value: T): Result<T> =
            Result(value, true)

        fun <T> failure(failureMessage: String): Result<T> =
            Result(failureMessage, false)
    }
}
