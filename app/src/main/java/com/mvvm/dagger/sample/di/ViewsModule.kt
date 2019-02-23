package com.mvvm.dagger.sample.di

import com.mvvm.dagger.sample.login.LoginModule
import com.mvvm.dagger.sample.main.MainModule
import com.mvvm.dagger.sample.photos.PhotosModule
import com.mvvm.dagger.sample.photos.detail.PhotoDetailModule
import com.mvvm.dagger.sample.places.PlacesModule
import com.mvvm.dagger.sample.register.RegisterModule
import com.mvvm.dagger.sample.splash.SplashModule
import dagger.Module

@Module(
        includes = [
            SplashModule::class,
            RegisterModule::class,
            LoginModule::class,
            MainModule::class,
            PlacesModule::class,
            PhotosModule::class,
            PhotoDetailModule::class
        ]
)
abstract class ViewsModule