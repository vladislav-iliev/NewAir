package com.example.newair.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newair.data.sensors.SensorDataManager
import com.example.newair.data.user_locations.UserLocation
import com.example.newair.data.user_locations.UserLocationsManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {
    private val userLocationsManager = UserLocationsManager()
    private val sensorDataManager = SensorDataManager()

    private val _uiState = MutableStateFlow(SensorUiState())
    val uiState = _uiState.asStateFlow()
    private var downloadJob: Job? = null

    fun addUserLocation(newLocation: UserLocation) {
        userLocationsManager.addUserLocation(newLocation)
    }

    fun addUserLocations(newLocations: List<UserLocation>) {
        userLocationsManager.addUserLocations(newLocations)
    }

    fun removeUserLocation(name: String) {
        val loc = userLocationsManager.getUserLocations().first { it.name.equals(name) }
        userLocationsManager.removeUserLocation(loc)
    }

    fun removeAllUserLocations() {
        userLocationsManager.removeAllUserLocations()
    }

    fun userLocationExists(name: String) = userLocationsManager.locationAlreadyAdded(name)

    fun downloadData() {
        downloadJob?.cancel()
        downloadJob = viewModelScope.launch {
            val userLocations = userLocationsManager.getUserLocations()
            val liveData = sensorDataManager.loadLiveSensors()
            val historyData = sensorDataManager.loadHistorySensors()
            _uiState.update { SensorUiState(userLocations, liveData, historyData) }
        }
    }
}