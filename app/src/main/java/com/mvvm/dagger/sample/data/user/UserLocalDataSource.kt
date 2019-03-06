package com.mvvm.dagger.sample.data.user

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.User
import com.mvvm.dagger.sample.livedata.DataRequest
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.data.preference.PreferenceManager
import com.mvvm.dagger.sample.data.room.UserDao
import com.mvvm.dagger.sample.webservice.LoginRequest
import com.mvvm.dagger.sample.webservice.LoginResponse
import com.mvvm.dagger.sample.webservice.RegisterRequest
import com.mvvm.dagger.sample.webservice.RegisterResponse
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(private val userDao: UserDao): IUserDataSource {

    override fun isLoggedIn(context: Context): Boolean = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override fun getUser(context: Context): LiveData<Event<User>> = object : DataRequest<User>() {
        override fun dataRequestToObserve(): LiveData<User> = userDao.getUser()
    }.performRequest()

    override fun saveUser(context: Context, user: User) {
        doAsync {
            userDao.saveUser(user)
        }
    }

    override fun logout(context: Context) = PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = throw UnsupportedOperationException()

}