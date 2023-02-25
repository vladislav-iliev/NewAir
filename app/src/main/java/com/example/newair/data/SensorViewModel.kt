package com.example.newair.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newair.data.sensors.Downloader
import com.example.newair.data.user_locations.UserLocation
import com.example.newair.data.user_locations.UserLocationsManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {
    private val userLocationsManager = UserLocationsManager()
    private val downloader = Downloader()
    private var downloadJob: Job? = null

    private val _uiState = MutableLiveData(SensorUiState(listOf(), listOf(), listOf()))
    val uiState: LiveData<SensorUiState> = _uiState

    private fun onUserLocationModified() {
        _uiState.value = _uiState.value!!.copy(userLocations = userLocationsManager.getUserLocations())
    }

    fun addUserLocation(loc: UserLocation) {
        userLocationsManager.addUserLocations(listOf(loc))
        onUserLocationModified()
    }

    fun addUserLocations(newLocations: List<UserLocation>) {
        userLocationsManager.removeAllUserLocations()
        userLocationsManager.addUserLocations(newLocations)
        onUserLocationModified()
    }

    fun removeUserLocation(name: String) {
        userLocationsManager.removeUserLocation(name)
        onUserLocationModified()
    }

    fun removeAllUserLocations() {
        userLocationsManager.removeAllUserLocations()
        onUserLocationModified()
    }

    fun userLocationExists(name: String) = userLocationsManager.locationAlreadyAdded(name)

    fun downloadData() {
        downloadJob?.cancel()
        downloadJob = viewModelScope.launch {
            val liveData = downloader.newLiveSensors()
            val historyData = downloader.newHistorySensors()
            _uiState.value = _uiState.value!!.copy(liveData = liveData, historyData = historyData)
        }
    }
}