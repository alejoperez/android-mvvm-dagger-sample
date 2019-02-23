package com.mvvm.dagger.sample.data

interface BaseRepositoryModule {
    companion object {
        const val LOCAL = "local"
        const val REMOTE = "remote"
    }
}