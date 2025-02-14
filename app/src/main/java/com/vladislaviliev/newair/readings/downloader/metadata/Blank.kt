package com.vladislaviliev.newair.readings.downloader.metadata

internal val Blank get() = Metadata("", "")
fun Metadata.isBlank() = this == Blank