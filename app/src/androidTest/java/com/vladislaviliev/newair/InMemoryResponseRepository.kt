package com.vladislaviliev.newair

import com.vladislaviliev.newair.readings.downloader.metadata.Metadata
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponse
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryResponseRepository : ResponseRepository {

    private val live = MutableStateFlow(LiveResponse(true, emptyList(), Metadata("", "")))
    private val history = MutableStateFlow(HistoryResponse(true, emptyList(), Metadata("", "")))

    override fun liveResponses() = live

    override fun historyResponses() = history

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }
}