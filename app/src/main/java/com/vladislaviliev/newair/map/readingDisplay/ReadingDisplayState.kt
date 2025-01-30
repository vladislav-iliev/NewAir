package com.vladislaviliev.newair.map.readingDisplay

import com.vladislaviliev.newair.readings.live.LiveReading

data class ReadingDisplayState(
    val isColorBlind: Boolean,
    val readings: Iterable<LiveReading>,
)