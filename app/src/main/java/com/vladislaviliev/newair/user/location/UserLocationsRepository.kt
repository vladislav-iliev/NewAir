package com.vladislaviliev.newair.user.location

import androidx.paging.PagingData
import com.vladislaviliev.newair.user.location.paging.Model
import kotlinx.coroutines.flow.Flow

interface UserLocationsRepository {

    suspend operator fun get(id: Int): UserLocation

    suspend fun add(name: String, lat: Double, lng: Double)

    suspend fun addInitial()

    suspend fun exists(name: String): Boolean

    suspend fun getLastId(): Int

    suspend fun delete(ids: Collection<Int>)

    suspend fun deleteAllExceptCity()

    fun newPagingSelect(): Flow<PagingData<Model>>

    fun newPagingDelete(): Flow<PagingData<Model>>
}