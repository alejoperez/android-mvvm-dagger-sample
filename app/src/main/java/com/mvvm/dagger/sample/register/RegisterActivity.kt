package com.mvvm.dagger.sample.register

import androidx.lifecycle.Observer
import com.mvvm.dagger.sample.BR
import com.mvvm.dagger.sample.R
import com.mvvm.dagger.sample.base.BaseActivity
import com.mvvm.dagger.sample.databinding.ActivityRegisterBinding
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.livedata.Status
import com.mvvm.dagger.sample.login.LoginActivity
import com.mvvm.dagger.sample.main.MainActivity
import com.mvvm.dagger.sample.utils.EditTextUtils
import com.mvvm.dagger.sample.webservice.RegisterResponse
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseActivity<RegisterViewModel,ActivityRegisterBinding>() {

    override fun getLayoutId() = R.layout.activity_register

    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.viewModel to viewModel,
            BR.etUtils to EditTextUtils
    )

    override fun initViewModel() {
        super.initViewModel()
        viewModel.registerResponse.observe(this, onRegisterResponseObserver)
    }

    override fun initView() {
        super.initView()
        dataBinding.tvGoToLogin.setOnClickListener { startActivity<LoginActivity>() }
    }

    private val onRegisterResponseObserver = Observer<Event<RegisterResponse>> {
        viewModel.hideProgress()
        if(it != null) {
            onRegisterResponse(it)
        } else {
            onRegisterFailure()
        }
    }

    private fun onRegisterResponse(response: Event<RegisterResponse>) {
        when(response.status) {
            Status.SUCCESS -> onRegisterSuccess()
            Status.FAILURE -> onRegisterFailure()
            Status.NETWORK_ERROR -> onNetworkError()
            else -> Unit
        }
    }

    private fun onRegisterSuccess() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onRegisterFailure() = showAlert(R.string.error_user_already_exists)

}
