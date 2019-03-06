package com.mvvm.dagger.sample.data.places

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.BaseRepositoryModule
import com.mvvm.dagger.sample.data.room.Place
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.livedata.NetworkRequest
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlacesRepository
@Inject
constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IPlacesDataSource,
            @Named(BaseRepositoryModule.REMOTE) private val remoteDataSource: IPlacesDataSource) : IPlacesDataSource {


    private var hasCache = false

    override fun getPlaces(context: Context): LiveData<Event<List<Place>>> {
        return if (hasCache) {
            localDataSource.getPlaces(context)
        } else {
            object : NetworkRequest<Event<List<Place>>>() {
                override fun processBeforeDispatch(response: Event<List<Place>>) {
                    response.peekData()?.let {
                        savePlaces(context, it)
                        hasCache = true
                    }
                }

                override fun networkRequestToObserve(): LiveData<Event<List<Place>>> = remoteDataSource.getPlaces(context)

            }.performRequest()
        }
    }

    override fun savePlaces(context: Context,places: List<Place>) {
        localDataSource.savePlaces(context, places)
    }

    fun invalidateCache() {
        hasCache = false
    }
}