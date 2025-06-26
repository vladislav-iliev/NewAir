package com.vladislaviliev.newair.screens.map.reading.state

import androidx.annotation.StringRes
import com.vladislaviliev.air.readings.live.LiveReading

data class State(
    @get:StringRes val message: Int,
    val errorMessage: String,
    val timestamp: String,
    val readings: Iterable<LiveReading>
)