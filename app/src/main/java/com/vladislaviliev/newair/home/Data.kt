package com.vladislaviliev.newair.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladislaviliev.newair.UserLocation
import java.util.Collections

internal class Data : ViewModel() {

    private val _locations = mutableListOf<CharSequence>()
    val locations: List<CharSequence> = Collections.unmodifiableList(_locations)

    private val _position: MutableLiveData<Int> = MutableLiveData(0)
    val position: LiveData<Int> = _position
    val positionInt get() = position.value!!

    fun setLocations(userLocations: List<UserLocation>) {
        _locations.clear()
        _locations.add("City")
        _locations.addAll(userLocations.map { it.name })
    }

    fun setPosition(pos: Int) {
        _position.value = pos
    }

    fun getCurrentLatLng(userLocations: List<UserLocation>) = if (positionInt == 0) null else userLocations[positionInt - 1].latLng
}