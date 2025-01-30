package com.vladislaviliev.newair.content.home.mainScreen.state

import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.readings.ReadingType
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.calculations.Maths.closestReadingTo
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.user.location.UserLocation

object HomeScreenStateTransformers {

    fun homeStateOf(
        isColorBlind: Boolean, userLocation: UserLocation, response: LiveResponse,
    ): HomeScreenState {

        val name = userLocation.name

        if (response.errorMsg.isNotEmpty()) return HomeScreenStateUnspecified.value.copy(
            location = name, message = response.errorMsg, timestamp = response.timestamp
        )

        if (response.readings.isEmpty()) return HomeScreenStateUnspecified.value.copy(
            location = name,
            message = HomeScreenStateConstants.NO_DATA,
            timestamp = response.timestamp
        )

        val readings = response.readings
        val latitude = userLocation.latitude
        val longitude = userLocation.longitude
        val pollution = readings.closestReadingTo(latitude, longitude, ReadingType.PM10)

        return HomeScreenState(
            Health.getColor(isColorBlind, pollution),
            Color.White,
            name,
            pollution.toString(),
            Health.getHealthMessage(pollution),
            readings.closestReadingTo(latitude, longitude, ReadingType.TEMP).toString(),
            readings.closestReadingTo(latitude, longitude, ReadingType.HUMID).toString(),
            response.timestamp,
        )
    }
}