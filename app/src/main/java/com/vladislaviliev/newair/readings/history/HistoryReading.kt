package com.vladislaviliev.newair.readings.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladislaviliev.newair.readings.ReadingType

@Entity
data class HistoryReading(

    @PrimaryKey
    val daysBefore: Int,

    val type: ReadingType,

    val measure: Double,
)