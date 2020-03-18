package com.master.myexchanges.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.master.myexchanges.domain.User

@Entity
data class UserData(
    @PrimaryKey val id: String,
    @ColumnInfo val name : String,
    @ColumnInfo val username : String,
    @ColumnInfo val email : String,
    @ColumnInfo val password : String,
    @ColumnInfo var signedIn : Boolean = false
)

fun UserData.toUser() =
    User(this.id,
        this.name,
        this.username,
        this.email,
        this.password)
