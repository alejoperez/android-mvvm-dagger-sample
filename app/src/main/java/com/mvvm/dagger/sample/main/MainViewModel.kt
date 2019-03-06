package com.mvvm.dagger.sample.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mvvm.dagger.sample.base.BaseViewModel
import com.mvvm.dagger.sample.data.room.User
import com.mvvm.dagger.sample.data.user.UserRepository
import com.mvvm.dagger.sample.livedata.Event
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    private val getUserEvent = MutableLiveData<Event<Unit>>()

    val user: LiveData<Event<User>> = Transformations.switchMap(getUserEvent) {
        userRepository.getUser(getApplication())
    }

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        getUserEvent.value = Event.loading()
    }

    fun logout() {
        onLogoutSuccess.value = Event.success(userRepository.logout(getApplication()))
    }

}