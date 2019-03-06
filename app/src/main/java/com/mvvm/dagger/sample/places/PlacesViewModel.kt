package com.mvvm.dagger.sample.places

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mvvm.dagger.sample.base.BaseViewModel
import com.mvvm.dagger.sample.data.room.Place
import com.mvvm.dagger.sample.data.places.PlacesRepository
import com.mvvm.dagger.sample.livedata.Event
import javax.inject.Inject

class PlacesViewModel @Inject constructor(application: Application,private val placesRepository: PlacesRepository): BaseViewModel(application) {

    private val placesEvent = MutableLiveData<Event<Unit>>()
    val places: LiveData<Event<List<Place>>> = Transformations.switchMap(placesEvent) {
        placesRepository.getPlaces(getApplication())
    }

    fun getPlaces() {
        placesEvent.value = Event.loading()
    }

}