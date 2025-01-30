package com.vladislaviliev.newair.content.map.readingMap

import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse

data class ReadingMapState(val isColorBlind: Boolean, val response: LiveResponse)