package com.vladislaviliev.newair.screens.graph.state

import androidx.annotation.StringRes
import com.vladislaviliev.air.readings.history.HistoryReading

data class State(
    @get:StringRes val message: Int,
    val errorMessage: String,
    val timestamp: String,
    val readings: Collection<HistoryReading>,
)