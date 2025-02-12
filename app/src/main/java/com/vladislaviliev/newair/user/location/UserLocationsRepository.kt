package com.vladislaviliev.newair.user.location

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class UserLocationsRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: Dao
) {
    suspend operator fun get(id: Int) = withContext(ioDispatcher) {
        dao.get(id) ?: throw NoSuchElementException()
    }

    suspend fun add(name: String, lat: Double, lng: Double) = scope.async(ioDispatcher) {
        dao.upsert(UserLocation(0, name, lat, lng))
    }.await()

    suspend fun addInitial() = scope.async(ioDispatcher) {
        dao.upsert(City.value)
    }.await()

    suspend fun getLastId() = withContext(ioDispatcher) { dao.getLastId() }

    suspend fun exists(name: String) = withContext(ioDispatcher) { dao.exists(name) }

    suspend fun delete(ids: Collection<Int>) = scope.async(ioDispatcher) {
        dao.delete(ids)
    }.await()

    suspend fun deleteAllExceptCity() = scope.async(ioDispatcher) {
        dao.deleteAllExcept(City.value.id)
    }.await()

    fun newPagingSourceSelect() = dao.newPagingSource()

    fun newPagingSourceDelete() = dao.newPagingSource(City.value.id)
}