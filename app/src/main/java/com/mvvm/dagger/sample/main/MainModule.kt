package com.mvvm.dagger.sample.main

import androidx.lifecycle.ViewModel
import com.mvvm.dagger.sample.di.ActivityScope
import com.mvvm.dagger.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel

}