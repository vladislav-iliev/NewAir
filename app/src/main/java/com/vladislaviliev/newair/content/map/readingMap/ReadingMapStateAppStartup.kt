package com.vladislaviliev.newair.content.map.readingMap

import com.vladislaviliev.newair.readings.downloader.responses.LiveResponseAppStartup

object ReadingMapStateAppStartup {
    val value = ReadingMapState(false, LiveResponseAppStartup.value)
}