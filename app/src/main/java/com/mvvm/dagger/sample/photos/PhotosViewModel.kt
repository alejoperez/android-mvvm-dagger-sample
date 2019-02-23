package com.mvvm.dagger.sample.photos

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.ObservableBoolean
import com.mvvm.dagger.sample.base.BaseViewModel
import com.mvvm.dagger.sample.data.room.Photo
import com.mvvm.dagger.sample.data.photos.PhotosRepository
import com.mvvm.dagger.sample.livedata.Event
import javax.inject.Inject

class PhotosViewModel @Inject constructor(application: Application, private val photosRepository: PhotosRepository): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    private val getPhotos = MutableLiveData<Event<Unit>>()

    val photos: LiveData<Event<List<Photo>>> = Transformations.switchMap(getPhotos) {
        photosRepository.getPhotos(getApplication())
    }

    fun getPhotos() {
        showProgress()
        getPhotos.value = Event.loading()
    }

    private fun showProgress() = isLoading.set(true)

    fun hideProgress() = isLoading.set(false)

}