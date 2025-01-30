package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.downloader.metadata.MetadataConstants

data object LiveResponseAppStartup {
    val value = LiveResponse(
        emptyList(),
        MetadataConstants.METADATA_LOADING,
        MetadataConstants.METADATA_LOADING
    )
}