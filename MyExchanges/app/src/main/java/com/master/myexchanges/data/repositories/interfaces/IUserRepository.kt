package com.master.myexchanges.data.repositories.interfaces

import com.master.myexchanges.domain.User
import com.master.myexchanges.util.Result

interface IUserRepository {
    suspend fun login(usernameOrEmail: String, password: String): Result<User>

    suspend fun getLoggedUser(): Result<User>

    suspend fun logout(user: User)

    suspend fun register(user: User): Result<User>
}