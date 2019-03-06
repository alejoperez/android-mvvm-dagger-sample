package com.mvvm.dagger.sample.data.photos

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.BaseRepositoryModule
import com.mvvm.dagger.sample.data.room.Photo
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.livedata.NetworkRequest
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PhotosRepository
@Inject
constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IPhotosDataSource,
            @Named(BaseRepositoryModule.REMOTE) private val  remoteDataSource: IPhotosDataSource) : IPhotosDataSource {


    private var hasCache = false

    override fun getPhotos(context: Context): LiveData<Event<List<Photo>>> {
        return if (hasCache) {
            localDataSource.getPhotos(context)
        } else {
            object : NetworkRequest<Event<List<Photo>>>() {

                override fun processBeforeDispatch(response: Event<List<Photo>>) {
                    response.peekData()?.let {
                        savePhotos(context, it)
                        hasCache = true
                    }
                }

                override fun networkRequestToObserve(): LiveData<Event<List<Photo>>> = remoteDataSource.getPhotos(context)

            }.performRequest()
        }
    }

    override fun savePhotos(context: Context,photos: List<Photo>) {
        localDataSource.savePhotos(context, photos)
    }

    fun invalidateCache() {
        hasCache = false
    }
}