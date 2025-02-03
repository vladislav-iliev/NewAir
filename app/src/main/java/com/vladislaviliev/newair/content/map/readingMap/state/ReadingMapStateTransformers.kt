package com.vladislaviliev.newair.content.map.readingMap.state

import com.vladislaviliev.newair.content.ScreenStateConstants
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataNotFound
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse

object ReadingMapStateTransformers {

    private fun messageStateOf(msg: String) = ReadingMapStateLoading.value.copy(message = msg)

    fun stateOf(isColorBlind: Boolean, response: LiveResponse): ReadingMapState {
        if (response.isLoading) return ReadingMapStateLoading.value
        val metadata = response.metadata
        if (metadata == MetadataNotFound.value) return messageStateOf(ScreenStateConstants.NO_DATA)
        if (metadata.errorMsg.isNotBlank()) return messageStateOf(metadata.errorMsg)
        if (response.readings.isEmpty()) return messageStateOf(metadata.timestamp)
        return ReadingMapState(isColorBlind, response.readings, metadata.timestamp)
    }
}