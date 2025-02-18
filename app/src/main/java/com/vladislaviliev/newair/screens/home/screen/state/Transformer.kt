package com.vladislaviliev.newair.screens.home.screen.state

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.vladislaviliev.air.readings.ReadingType
import com.vladislaviliev.air.readings.calculations.Health
import com.vladislaviliev.air.readings.calculations.Maths.average
import com.vladislaviliev.air.readings.calculations.Maths.closestReadingTo
import com.vladislaviliev.air.readings.downloader.metadata.isBlank
import com.vladislaviliev.air.readings.downloader.responses.LiveResponse
import com.vladislaviliev.air.readings.live.LiveReading
import com.vladislaviliev.air.user.location.UserLocation
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.screens.home.CITY_NAME

class Transformer {

    private fun unknownStateOf(
        location: String, @StringRes message: Int, errorMessage: String, timestamp: String
    ) = Loading.value.copy(location, message, errorMessage, timestamp)

    fun stateOf(userLocation: UserLocation, response: LiveResponse): State {

        val locationName = userLocation.name

        if (response.isLoading) return Loading.value.copy(location = locationName)

        val metadata = response.metadata

        if (metadata.isBlank()) return unknownStateOf(
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

        val shouldBeAverage = locationName == CITY_NAME
        fun Collection<LiveReading>.getReading(type: ReadingType) =
            if (shouldBeAverage) average() else closestReadingTo(latitude, longitude, type)

        val pollution = readings.getReading(ReadingType.PM10)

        return State(
            locationName,
            Health.getHealthMessage(pollution),
            "",
            metadata.timestamp,
            pollution.toString(),
            readings.getReading(ReadingType.TEMP).toString() + "°C",
            readings.getReading(ReadingType.HUMID).toString() + "%",
            Health.getColor(pollution),
            Color.White,
        )
    }
}