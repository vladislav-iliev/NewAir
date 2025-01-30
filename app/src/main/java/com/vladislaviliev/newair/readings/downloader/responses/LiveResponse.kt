package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.live.LiveReading

data class LiveResponse(
    val readings: List<LiveReading>,
    val errorMsg: String,
    val timestamp: String
)
