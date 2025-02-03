package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.history.HistoryReading
import com.vladislaviliev.newair.readings.downloader.metadata.Metadata

data class HistoryResponse(
    val isLoading: Boolean,
    val readings: List<HistoryReading>,
    val metadata: Metadata,
)