package com.mvvm.dagger.sample.places

import android.arch.lifecycle.ViewModel
import com.mvvm.dagger.sample.di.FragmentScope
import com.mvvm.dagger.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PlacesModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFragment(): PlacesFragment

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindViewModel(viewModel: PlacesViewModel): ViewModel

}