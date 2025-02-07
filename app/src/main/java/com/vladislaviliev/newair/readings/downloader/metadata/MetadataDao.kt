package com.vladislaviliev.newair.readings.downloader.metadata

import kotlinx.coroutines.flow.Flow

interface MetadataDao {
    val data: Flow<Metadata>
    suspend fun store(metadata: Metadata)
    suspend fun clear()
}