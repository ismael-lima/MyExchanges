package com.master.myexchanges.data.repositories

import android.app.Application
import android.util.Log
import com.master.myexchanges.R
import com.master.myexchanges.data.database.dao.UserDao
import com.master.myexchanges.data.model.toUser
import com.master.myexchanges.data.repositories.interfaces.IUserRepository
import com.master.myexchanges.domain.User
import com.master.myexchanges.domain.toUserData
import com.master.myexchanges.util.Result

class UserRepository(private val app: Application, private val userDao: UserDao): IUserRepository {
    override suspend fun login(usernameOrEmail: String, password: String): Result<User> {
        val users = userDao.getUserSignIn(usernameOrEmail, password)
        return if (users.isEmpty()) {
            Result.failure<User>(app.getString(R.string.error_not_found_user))
        } else {
            val user = users.first()
            user.signedIn = true
            userDao.updateUser(user)
            Result.success(user.toUser())
        }
    }

    override suspend fun getLoggedUser() : Result<User>{
        val user = userDao.getSignedUser()?.
            find { u -> u.signedIn }?.toUser()
        user?.let{
            return Result.success(it)
        }
        return Result.failure<User>(app.getString(R.string.error_not_found_user))
    }

    override suspend fun logout(user: User) {
        val userData = user.toUserData()
        userData.signedIn = false
        userDao.updateUser(userData)
    }

    override suspend fun register(user: User): Result<User> {
        val users = userDao.getByUserNameOrEmail(user.username, user.email)
        return if (users.isEmpty()) {
            try {
                userDao.insert(user.toUserData())
                Result.success(user)
            } catch (e: Exception) {
                Log.d(app.packageName, e.message, e)
                Result.failure<User>(app.getString(R.string.error_on_registering))
            }
        } else {
            val isUsername = users.firstOrNull { u -> u.username == user.username } != null
            if (isUsername)
                Result.failure<User>(app.getString(R.string.error_username_already_registered))
            else
                Result.failure<User>(app.getString(R.string.error_email_already_registered))
        }
    }
}