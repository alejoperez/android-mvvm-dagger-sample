package com.mvvm.dagger.sample.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.mvvm.dagger.sample.R
import com.mvvm.dagger.sample.base.BaseViewModel
import com.mvvm.dagger.sample.data.user.UserRepository
import com.mvvm.dagger.sample.databinding.BindingAdapters
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.utils.checkField
import com.mvvm.dagger.sample.utils.getValueOrDefault
import com.mvvm.dagger.sample.webservice.LoginRequest
import com.mvvm.dagger.sample.webservice.LoginResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application,private val userRepository: UserRepository): BaseViewModel(application) {

    val email = ObservableField("")
    val password = ObservableField("")

    val emailError = ObservableInt(BindingAdapters.EMPTY)
    val passwordError = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableBoolean(false)

    private val loginEvent = MutableLiveData<Event<Unit>>()
    val loginResponse: LiveData<Event<LoginResponse>> = Transformations.switchMap(loginEvent) {
        userRepository.login(getApplication(), LoginRequest(email.getValueOrDefault(), password.getValueOrDefault()))
    }

    private fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    private fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    private fun isValidForm(): Boolean = isValidEmail() && isValidPassword()

    fun login() {
        if (isValidForm()) {
            showProgress()
            loginEvent.value = Event.loading()
        } else {
            emailError.checkField(R.string.error_invalid_email,isValidEmail())
            passwordError.checkField(R.string.error_empty_password,isValidPassword())
        }
    }

    private fun showProgress() = isLoading.set(true)

    fun hideProgress() = isLoading.set(false)
}