package com.mvvm.dagger.sample.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application): AndroidViewModel(application)