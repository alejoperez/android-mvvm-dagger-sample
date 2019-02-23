package com.mvvm.dagger.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.BaseRepositoryModule
import com.mvvm.dagger.sample.data.room.User
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.livedata.NetworkRequest
import com.mvvm.dagger.sample.data.preference.PreferenceManager
import com.mvvm.dagger.sample.webservice.LoginRequest
import com.mvvm.dagger.sample.webservice.LoginResponse
import com.mvvm.dagger.sample.webservice.RegisterRequest
import com.mvvm.dagger.sample.webservice.RegisterResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IUserDataSource,
                    @Named(BaseRepositoryModule.REMOTE) private val remoteDataSource: IUserDataSource) : IUserDataSource {

    override fun saveUser(context: Context,user: User) = localDataSource.saveUser(context,user)

    override fun getUser(context: Context) = localDataSource.getUser(context)

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = object  : NetworkRequest<Event<LoginResponse>>() {

        override fun processBeforeDispatch(response: Event<LoginResponse>) {
            response.peekData()?.let {
                PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,it.accessToken)
                localDataSource.saveUser(context, it.toUser())
            }
        }

        override fun networkRequestToObserve(): LiveData<Event<LoginResponse>> = remoteDataSource.login(context, request)

    }.performRequest()

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = object : NetworkRequest<Event<RegisterResponse>>(){

        override fun processBeforeDispatch(response: Event<RegisterResponse>) {
            response.peekData()?.let {
                PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                localDataSource.saveUser(context, it.toUser())
            }
        }

            override fun networkRequestToObserve(): LiveData<Event<RegisterResponse>> = remoteDataSource.register(context, request)

        }.performRequest()

    override fun isLoggedIn(context: Context): Boolean = localDataSource.isLoggedIn(context)

    override fun logout(context: Context) = localDataSource.logout(context)

}
