package com.example.newair.data

import com.example.newair.data.sensors.Sensor
import com.example.newair.data.user_locations.UserLocation

data class SensorUiState(
    val userLocations: List<UserLocation> = listOf(),
    val liveData: List<Sensor> = listOf(),
    val historyData: List<Double> = listOf(),
    var isLoading: Boolean = false
)