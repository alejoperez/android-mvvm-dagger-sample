package com.mvvm.dagger.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.User
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.webservice.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val api: IApi): IUserDataSource {

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = api.login(request)

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = api.register(request)

    override fun logout(context: Context) = throw UnsupportedOperationException()

    override fun getUser(context: Context): LiveData<Event<User>> = throw UnsupportedOperationException()

    override fun saveUser(context: Context, user: User) = throw UnsupportedOperationException()

    override fun isLoggedIn(context: Context): Boolean = throw UnsupportedOperationException()
}