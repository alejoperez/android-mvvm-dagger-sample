package com.mvvm.dagger.sample.data.photos

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.Photo
import com.mvvm.dagger.sample.data.room.PhotoDao
import com.mvvm.dagger.sample.livedata.DataRequest
import com.mvvm.dagger.sample.livedata.Event
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosLocalDataSource @Inject constructor(private val photoDao: PhotoDao): IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) {
        doAsync {
            photoDao.savePhotos(photos)
        }
    }

    override fun getPhotos(context: Context): LiveData<Event<List<Photo>>> {
        return object : DataRequest<List<Photo>>() {
            override fun dataRequestToObserve(): LiveData<List<Photo>> = photoDao.getPhotos()
        }.performRequest()
    }

}