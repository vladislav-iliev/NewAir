package com.example.newair.data

import com.example.newair.data.sensors.Sensor
import com.example.newair.data.user_locations.UserLocation

data class SensorUiState(
    val userLocations: List<UserLocation>,
    val liveData: List<Sensor>,
    val historyData: List<Double>
)