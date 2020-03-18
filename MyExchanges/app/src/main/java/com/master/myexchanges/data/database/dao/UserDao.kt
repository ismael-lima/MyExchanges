package com.master.myexchanges.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.master.myexchanges.data.model.UserData
import com.master.myexchanges.domain.User

@Dao
interface UserDao{
    @Query("SELECT * FROM UserData WHERE username = :username or email = :email")
    fun getByUserNameOrEmail(username: String, email: String): List<UserData>

    @Query("SELECT * FROM UserData")
    fun getSignedUser(): List<UserData>

    @Update
    fun updateUser(user: UserData)

    @Query("SELECT * FROM UserData WHERE (username = :usernameOrEmail or email = :usernameOrEmail) and password = :password")
    fun getUserSignIn(usernameOrEmail: String, password: String): List<UserData>

    @Insert
    fun insert(user: UserData)
}