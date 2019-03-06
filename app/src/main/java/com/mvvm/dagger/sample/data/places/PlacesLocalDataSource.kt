package com.mvvm.dagger.sample.data.places

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.Place
import com.mvvm.dagger.sample.data.room.PlaceDao
import com.mvvm.dagger.sample.livedata.DataRequest
import com.mvvm.dagger.sample.livedata.Event
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesLocalDataSource @Inject constructor(private val placesDao: PlaceDao) : IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) {
        doAsync {
            placesDao.savePlaces(places)
        }
    }

    override fun getPlaces(context: Context): LiveData<Event<List<Place>>> = object : DataRequest<List<Place>>() {
        override fun dataRequestToObserve(): LiveData<List<Place>> = placesDao.getPlaces()

    }.performRequest()

}