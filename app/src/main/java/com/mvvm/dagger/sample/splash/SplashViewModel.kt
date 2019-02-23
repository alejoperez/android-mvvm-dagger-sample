package com.mvvm.dagger.sample.splash

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.dagger.sample.base.BaseViewModel
import com.mvvm.dagger.sample.data.user.UserRepository
import com.mvvm.dagger.sample.livedata.Event
import javax.inject.Inject

class SplashViewModel @Inject constructor(application: Application, private val userRepository: UserRepository): BaseViewModel(application) {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn() {
        isUserLoggedEvent.value = Event.success(userRepository.isLoggedIn(getApplication()))
    }

}