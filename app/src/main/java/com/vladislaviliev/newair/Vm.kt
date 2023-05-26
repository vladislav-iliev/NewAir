package com.vladislaviliev.newair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.data.Downloader
import com.vladislaviliev.newair.data.Sensor
import com.vladislaviliev.newair.data.UserLocation
import kotlinx.coroutines.launch
import java.util.Collections

class Vm : ViewModel() {
    private val _userLocations = mutableListOf<UserLocation>()
    val userLocations: List<UserLocation> get() = Collections.unmodifiableList(_userLocations)

    private val _liveSensors = MutableLiveData(emptyList<Sensor>())
    val liveSensors: LiveData<List<Sensor>> = _liveSensors

    private val _historySensors = mutableListOf<Double>()
    val historySensors: List<Double> = Collections.unmodifiableList(_historySensors)

    fun addUserLocation(loc: UserLocation) = _userLocations.add(loc)

    fun addUserLocations(newLocations: Collection<UserLocation>) = _userLocations.addAll(newLocations)

    fun removeUserLocation(name: String) = _userLocations.remove(userLocations.first { it.name == name })

    fun removeAllUserLocations() = _userLocations.clear()

    fun userLocationExists(name: String) = _userLocations.any { it.name == name }

    fun downloadData() {
        viewModelScope.launch {
            val downloader = Downloader()
            _liveSensors.value = downloader.newLiveSensors()
            _historySensors.clear()
            _historySensors.addAll(downloader.newHistorySensors())
        }
    }
}