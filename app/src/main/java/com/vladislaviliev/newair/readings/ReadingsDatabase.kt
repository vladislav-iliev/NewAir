package com.vladislaviliev.newair.readings

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladislaviliev.newair.readings.history.HistoryDao
import com.vladislaviliev.newair.readings.history.HistoryReading
import com.vladislaviliev.newair.readings.live.LiveDao
import com.vladislaviliev.newair.readings.live.LiveReading

@Database(entities = [LiveReading::class, HistoryReading::class], version = 1, exportSchema = false)
abstract class ReadingsDatabase : RoomDatabase() {

    abstract fun liveDao(): LiveDao
    abstract fun historyDao(): HistoryDao
}