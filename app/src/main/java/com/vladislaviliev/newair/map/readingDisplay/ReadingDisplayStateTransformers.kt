package com.vladislaviliev.newair.map.readingDisplay

import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse

object ReadingDisplayStateTransformers {

    fun stateOf(isColorBlind: Boolean, response: LiveResponse) =
        ReadingDisplayState(isColorBlind, response.readings)
}