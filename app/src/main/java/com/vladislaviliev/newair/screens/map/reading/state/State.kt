package com.vladislaviliev.newair.screens.map.reading.state

import androidx.annotation.StringRes
import com.vladislaviliev.newair.readings.live.LiveReading

data class State(
    @StringRes val message: Int,
    val errorMessage: String,
    val timestamp: String,
    val isColorBlind: Boolean,
    val readings: Iterable<LiveReading>
)