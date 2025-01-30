package com.vladislaviliev.newair.user.location

import com.vladislaviliev.newair.user.location.paging.UserLocationsPagingProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class UserLocationsRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: UserLocationDao,
    private val pagingProvider: UserLocationsPagingProvider,
) {

    /** @throws NoSuchElementException if location does not exist **/
    suspend fun getLocation(id: Int) = withContext(ioDispatcher) {
        dao.getById(id) ?: throw NoSuchElementException()
    }

    suspend fun addInitial() = scope.async(ioDispatcher) {
        dao.upsert(DefaultUserLocation.value)
        dao.upsert(SampleLocations.locations)
    }.await()

    suspend fun existsByName(name: String) = withContext(ioDispatcher) { dao.existsByName(name) }

    suspend fun addLocation(name: String, lat: Double, lng: Double) = scope.async(ioDispatcher) {
        dao.upsert(UserLocation(0, name, lat, lng))
    }.await()

    suspend fun getLastLocationId() = withContext(ioDispatcher) { dao.getLastLocationId() }

    suspend fun deleteLocations(ids: Collection<Int>) = scope.async(ioDispatcher) {
        dao.deleteLocationsByIds(ids)
    }.await()

    suspend fun deleteAll() = scope.async(ioDispatcher) {
        dao.deleteAllExcept(DefaultUserLocation.value.id)
    }.await()

    fun pagingFlowSelect() = pagingProvider.newPagingFlowSelect()
    fun pagingFlowDelete() = pagingProvider.newPagingFlowDelete()
}