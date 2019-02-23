package com.mvvm.dagger.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.Photo
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.webservice.IApi
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRemoteDataSource @Inject constructor(private val api: IApi) : IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) = throw UnsupportedOperationException()

    override fun getPhotos(context: Context): LiveData<Event<List<Photo>>> = api.getPhotos()

}