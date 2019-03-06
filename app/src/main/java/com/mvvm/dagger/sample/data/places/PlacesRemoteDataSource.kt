package com.mvvm.dagger.sample.data.places

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.Place
import com.mvvm.dagger.sample.livedata.Event
import com.mvvm.dagger.sample.webservice.IApi
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesRemoteDataSource @Inject constructor(private val api: IApi)  : IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) = throw UnsupportedOperationException()

    override fun getPlaces(context: Context): LiveData<Event<List<Place>>> = api.getPlaces()

}