package com.mvvm.dagger.sample.login

import android.arch.lifecycle.ViewModel
import com.mvvm.dagger.sample.di.ActivityScope
import com.mvvm.dagger.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeActivity(): LoginActivity

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewModel: LoginViewModel): ViewModel

}