package com.vladislaviliev.newair.screens.graph.state

import androidx.annotation.StringRes
import com.vladislaviliev.air.readings.downloader.metadata.isBlank
import com.vladislaviliev.air.readings.downloader.responses.HistoryResponse
import com.vladislaviliev.newair.R

class Transformer {

    private fun messageStateOf(@StringRes msg: Int, errorMsg: String, timestamp: String) =
        Loading.value.copy(msg, errorMsg, timestamp)

    fun stateOf(response: HistoryResponse): State {
        if (response.isLoading) return Loading.value

        val metadata = response.metadata

        if (metadata.isBlank()) return messageStateOf(R.string.no_data, "", "")

        if (metadata.errorMsg.isNotBlank()) return messageStateOf(
            R.string.error, metadata.errorMsg, metadata.timestamp
        )
        if (response.readings.isEmpty()) return messageStateOf(
            R.string.no_data, "", metadata.timestamp
        )
        return State(
            R.string.empty_placeholder, "", metadata.timestamp, response.readings
        )
    }
}