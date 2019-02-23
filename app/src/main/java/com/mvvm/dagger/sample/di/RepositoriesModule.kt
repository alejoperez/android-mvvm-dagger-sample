package com.mvvm.dagger.sample.di

import com.mvvm.dagger.sample.data.photos.PhotosRepositoryModule
import com.mvvm.dagger.sample.data.places.PlacesRepositoryModule
import com.mvvm.dagger.sample.data.user.UserRepositoryModule
import dagger.Module

@Module(
        includes = [
            UserRepositoryModule::class,
            PlacesRepositoryModule::class,
            PhotosRepositoryModule::class
        ]
)
abstract class RepositoriesModule