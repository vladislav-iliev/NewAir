package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.history.HistoryReading
import com.vladislaviliev.newair.readings.live.LiveReading

data class Response(
    val liveReadings: List<LiveReading>,
    val historyReadings: List<HistoryReading>,
    val errorMsg: String,
    val timestamp: String,
)