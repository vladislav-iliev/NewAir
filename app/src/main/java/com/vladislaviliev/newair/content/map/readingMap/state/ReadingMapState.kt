package com.vladislaviliev.newair.content.map.readingMap.state

import com.vladislaviliev.newair.readings.live.LiveReading

data class ReadingMapState(
    val isColorBlind: Boolean, val readings: Iterable<LiveReading>, val message: String,
)