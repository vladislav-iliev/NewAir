package com.vladislaviliev.newair.content.home.mainScreen.state

import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.content.ScreenStateConstants
import com.vladislaviliev.newair.readings.ReadingType
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.calculations.Maths.closestReadingTo
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataNotFound
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.user.location.UserLocation

object HomeScreenStateTransformers {

    private fun unknownStateOf(location: String, message: String, timestamp: String) =
        HomeScreenStateLoading.value.copy(
            location = location, message = message, timestamp = timestamp
        )

    fun stateOf(
        isColorBlind: Boolean, userLocation: UserLocation, response: LiveResponse,
    ): HomeScreenState {

        val locationName = userLocation.name

        if (response.isLoading) return HomeScreenStateLoading.value.copy(location = locationName)

        val metadata = response.metadata

        if (metadata == MetadataNotFound.value) return unknownStateOf(
            locationName, ScreenStateConstants.NO_DATA, ScreenStateConstants.NO_DATA
        )

        if (metadata.errorMsg.isNotBlank())
            return unknownStateOf(locationName, metadata.errorMsg, metadata.timestamp)

        if (response.readings.isEmpty())
            return unknownStateOf(locationName, ScreenStateConstants.NO_DATA, metadata.timestamp)

        val readings = response.readings
        val latitude = userLocation.latitude
        val longitude = userLocation.longitude
        val pollution = readings.closestReadingTo(latitude, longitude, ReadingType.PM10)

        return HomeScreenState(
            Health.getColor(isColorBlind, pollution),
            Color.White,
            locationName,
            pollution.toString(),
            Health.getHealthMessage(pollution),
            readings.closestReadingTo(latitude, longitude, ReadingType.TEMP).toString(),
            readings.closestReadingTo(latitude, longitude, ReadingType.HUMID).toString(),
            metadata.timestamp,
        )
    }
}