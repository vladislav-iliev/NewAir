package com.vladislaviliev.newair.readings.history

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Upsert
    suspend fun upsert(historyReading: Iterable<HistoryReading>)

    @Query("SELECT * FROM HistoryReading")
    suspend fun getAll(): List<HistoryReading>

    @Query("SELECT * FROM HistoryReading")
    fun getAllFlow(): Flow<List<HistoryReading>>

    @Query("DELETE FROM HistoryReading")
    suspend fun deleteAll()
}