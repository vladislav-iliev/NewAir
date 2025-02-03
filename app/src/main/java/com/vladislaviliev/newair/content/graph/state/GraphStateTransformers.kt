package com.vladislaviliev.newair.content.graph.state

import com.vladislaviliev.newair.content.ScreenStateConstants
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataNotFound
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponse

object GraphStateTransformers {

    private fun messageStateOf(msg: String) = GraphStateLoading.value.copy(message = msg)

    fun stateOf(response: HistoryResponse): GraphState {
        if (response.isLoading) return GraphStateLoading.value
        val metadata = response.metadata
        if (metadata == MetadataNotFound.value) return messageStateOf(ScreenStateConstants.NO_DATA)
        if (metadata.errorMsg.isNotBlank()) return messageStateOf(metadata.errorMsg)
        if (response.readings.isEmpty()) return messageStateOf(metadata.timestamp)
        return GraphState(response.readings, metadata.timestamp)
    }
}