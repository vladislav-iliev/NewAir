package com.vladislaviliev.newair.screens.map.reading.state

import androidx.annotation.StringRes
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataNotFound
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.screens.StateConstants

object Transformer {

    private fun messageStateOf(@StringRes msg: Int, errorMsg: String, timestamp: String) =
        Loading.value.copy(msg, errorMsg, timestamp)

    fun stateOf(response: LiveResponse): State {
        if (response.isLoading) return Loading.value

        val metadata = response.metadata

        if (metadata == MetadataNotFound.value) return messageStateOf(
            StateConstants.noData, "", ""
        )
        if (metadata.errorMsg.isNotBlank()) return messageStateOf(
            StateConstants.error, metadata.errorMsg, metadata.timestamp
        )
        if (response.readings.isEmpty()) return messageStateOf(
            StateConstants.noData, "", metadata.timestamp
        )
        return State(
            StateConstants.emptyPlaceholder,
            "",
            metadata.timestamp,
            response.readings
        )
    }
}