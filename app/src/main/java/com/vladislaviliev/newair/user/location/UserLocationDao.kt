package com.vladislaviliev.newair.user.location

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserLocationDao {

    @Upsert
    suspend fun upsert(userLocation: UserLocation)

    @Upsert
    suspend fun upsert(userLocations: Collection<UserLocation>)

    @Query("SELECT * FROM UserLocation WHERE id = :id")
    suspend fun get(id: Int): UserLocation?

    @Query("SELECT EXISTS(SELECT 1 FROM UserLocation WHERE name = :name COLLATE NOCASE)")
    suspend fun exists(name: String): Boolean

    @Query("SELECT id FROM UserLocation ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int

    @Query("DELETE FROM UserLocation WHERE id IN (:locationIds)")
    suspend fun delete(locationIds: Collection<Int>)

    @Query("DELETE FROM UserLocation WHERE id != :except")
    suspend fun deleteAllExcept(except: Int)

    @Query("SELECT * FROM UserLocation WHERE id NOT IN (:excludedIds) ORDER BY name COLLATE NOCASE ASC")
    fun newPagingSource(excludedIds: Collection<Int>): PagingSource<Int, UserLocation>
}