package com.mvvm.dagger.sample.webservice

import com.mvvm.dagger.sample.data.room.User


class RegisterResponse(val id: Long,
                       val name: String,
                       val email: String,
                       val accessToken: String) {

    fun toUser(): User = User(id, name, email)
}

class LoginResponse(val success: Boolean,
                    val id: Long,
                    val name: String,
                    val email: String,
                    val accessToken: String) {

    fun toUser(): User = User(id, name, email)
}