package com.master.myexchanges.domain

import com.master.myexchanges.data.model.UserData
import java.io.Serializable
import java.util.*

class User(
    var id : String,
    var name : String,
    var username : String,
    var email : String,
    var password : String
):Serializable{
    init {
        if (id.isBlank()){
            id = UUID.randomUUID().toString()
        }
    }
}

fun User.toUserData() =
    UserData(this.id,
        this.name,
        this.username,
        this.email,
        this.password)