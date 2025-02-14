package com.vladislaviliev.newair.screens.home.screen.state

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.readings.ReadingType
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.calculations.Maths.closestReadingTo
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataNotFound
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.user.location.UserLocation

object Transformer {

    private fun unknownStateOf(
        location: String, @StringRes message: Int, errorMessage: String, timestamp: String
    ) = Loading.value.copy(location, message, errorMessage, timestamp)

    fun stateOf(userLocation: UserLocation, response: LiveResponse): State {

        val locationName = userLocation.name

        if (response.isLoading) return Loading.value.copy(location = locationName)

        val metadata = response.metadata

        if (metadata == MetadataNotFound.value) return unknownStateOf(
            locationName, R.string.no_data, "", ""
        )

        if (metadata.errorMsg.isNotBlank()) return unknownStateOf(
            locationName, R.string.error, metadata.errorMsg, metadata.timestamp
        )

        if (response.readings.isEmpty()) return unknownStateOf(
            locationName, R.string.no_data, "", metadata.timestamp
        )

        val readings = response.readings
        val latitude = userLocation.latitude
        val longitude = userLocation.longitude
        val pollution = readings.closestReadingTo(latitude, longitude, ReadingType.PM10)

        return State(
            locationName,
            Health.getHealthMessage(pollution),
            "",
            metadata.timestamp,
            pollution.toString(),
            readings.closestReadingTo(latitude, longitude, ReadingType.TEMP).toString() + "°C",
            readings.closestReadingTo(latitude, longitude, ReadingType.HUMID).toString() + "%",
            Health.getColor(pollution),
            Color.White,
        )
    }
}