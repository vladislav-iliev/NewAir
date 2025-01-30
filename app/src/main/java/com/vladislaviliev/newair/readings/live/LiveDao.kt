package com.vladislaviliev.newair.readings.live

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LiveDao {

    @Upsert
    suspend fun upsert(readings: Collection<LiveReading>)

    @Query("SELECT * FROM LiveReading")
    suspend fun getAll(): List<LiveReading>

    @Query("SELECT * FROM LiveReading")
    fun getAllFlow(): Flow<List<LiveReading>>

    @Query("DELETE FROM LiveReading")
    suspend fun deleteAll()
}