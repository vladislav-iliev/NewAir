package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.history.HistoryReading

data class HistoryResponse(
    val readings: List<HistoryReading>,
    val errorMsg: String,
    val timestamp: String
)
