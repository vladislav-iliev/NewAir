package com.vladislaviliev.newair.dao

import com.vladislaviliev.newair.readings.downloader.metadata.Blank
import com.vladislaviliev.newair.readings.downloader.metadata.Dao
import com.vladislaviliev.newair.readings.downloader.metadata.Metadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryMetadataDao : Dao {

    private val _data = MutableStateFlow(Metadata.Blank)

    override val data: Flow<Metadata> = _data

    override suspend fun store(metadata: Metadata) {
        _data.emit(metadata)
    }

    override suspend fun clear() {
        _data.emit(Metadata.Blank)
    }
}