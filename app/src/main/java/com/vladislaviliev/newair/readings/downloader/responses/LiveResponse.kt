package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.live.LiveReading
import com.vladislaviliev.newair.readings.downloader.metadata.Metadata

data class LiveResponse(
    val isLoading: Boolean, val readings: List<LiveReading>, val metadata: Metadata,
)
