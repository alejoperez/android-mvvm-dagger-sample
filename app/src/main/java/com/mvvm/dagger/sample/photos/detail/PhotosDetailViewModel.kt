package com.mvvm.dagger.sample.photos.detail

import android.app.Application
import com.mvvm.dagger.sample.base.BaseViewModel
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(application: Application): BaseViewModel(application)