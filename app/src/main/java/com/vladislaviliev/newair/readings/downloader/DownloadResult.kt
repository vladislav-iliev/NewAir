package com.vladislaviliev.newair.readings.downloader

import com.vladislaviliev.newair.readings.downloader.metadata.Metadata
import com.vladislaviliev.newair.readings.history.HistoryReading
import com.vladislaviliev.newair.readings.live.LiveReading

data class DownloadResult(
    val liveReadings: List<LiveReading>,
    val historyReadings: List<HistoryReading>,
    val metadata: Metadata,
)