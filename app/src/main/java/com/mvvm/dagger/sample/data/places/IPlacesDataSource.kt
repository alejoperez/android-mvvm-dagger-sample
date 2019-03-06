package com.mvvm.dagger.sample.data.places

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.dagger.sample.data.room.Place
import com.mvvm.dagger.sample.livedata.Event

interface IPlacesDataSource {

    fun getPlaces(context: Context): LiveData<Event<List<Place>>>

    fun savePlaces(context: Context, places: List<Place>)
}