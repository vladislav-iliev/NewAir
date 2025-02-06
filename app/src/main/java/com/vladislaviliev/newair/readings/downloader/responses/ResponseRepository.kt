package com.vladislaviliev.newair.readings.downloader.responses

import kotlinx.coroutines.flow.Flow

interface ResponseRepository {
    fun liveResponses(): Flow<LiveResponse>
    fun historyResponses(): Flow<HistoryResponse>
    suspend fun refresh()
}