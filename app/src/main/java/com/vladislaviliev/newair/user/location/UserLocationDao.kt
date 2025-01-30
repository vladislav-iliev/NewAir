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

    @Query("DELETE FROM UserLocation WHERE id != :except")
    suspend fun deleteAllExcept(except: Int)

    @Query("DELETE FROM UserLocation WHERE id IN (:locationIds)")
    suspend fun deleteLocationsByIds(locationIds: Collection<Int>)

    @Query("SELECT * FROM UserLocation WHERE id NOT IN (:excludedIds) ORDER BY name COLLATE NOCASE ASC")
    fun newPagingSource(excludedIds: Collection<Int>): PagingSource<Int, UserLocation>

    @Query("SELECT * FROM UserLocation WHERE id = :id")
    suspend fun getById(id: Int): UserLocation?

    @Query("SELECT EXISTS(SELECT 1 FROM UserLocation WHERE name = :name COLLATE NOCASE)")
    suspend fun existsByName(name: String): Boolean

    @Query("SELECT id FROM UserLocation ORDER BY id DESC LIMIT 1")
    suspend fun getLastLocationId(): Int
}