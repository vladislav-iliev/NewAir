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

    @Query("DELETE FROM UserLocation WHERE id IN (:ids)")
    suspend fun delete(ids: Collection<Int>)

    @Query("DELETE FROM UserLocation WHERE id != :id")
    suspend fun deleteAllExcept(id: Int)

    @Query("SELECT * FROM UserLocation WHERE id != :excluding ORDER BY name COLLATE NOCASE ASC")
    fun newPagingSource(excluding: Int = Int.MAX_VALUE): PagingSource<Int, UserLocation>
}