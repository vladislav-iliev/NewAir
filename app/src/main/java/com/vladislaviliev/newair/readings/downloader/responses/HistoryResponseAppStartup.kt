package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.downloader.metadata.MetadataConstants

data object HistoryResponseAppStartup {
    val value = HistoryResponse(
        emptyList(),
        MetadataConstants.METADATA_LOADING,
        MetadataConstants.METADATA_LOADING
    )
}