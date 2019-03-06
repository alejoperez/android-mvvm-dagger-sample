package com.mvvm.dagger.sample.photos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.databinding.ObservableBoolean
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