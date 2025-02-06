package com.vladislaviliev.newair.user.location

import com.vladislaviliev.newair.user.location.paging.PagingProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class UserLocationsRepositoryImpl(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: UserLocationDao,
    private val pagingProvider: PagingProvider,
): UserLocationsRepository {

    override suspend operator fun get(id: Int) = withContext(ioDispatcher) {
        dao.get(id) ?: throw NoSuchElementException()
    }

    override suspend fun add(name: String, lat: Double, lng: Double) = scope.async(ioDispatcher) {
        dao.upsert(UserLocation(0, name, lat, lng))
    }.await()

    override suspend fun addInitial() = scope.async(ioDispatcher) {
        dao.upsert(DefaultUserLocation.value)
        dao.upsert(SampleLocations.locations)
    }.await()

    override suspend fun getLastId() = withContext(ioDispatcher) { dao.getLastId() }

    override suspend fun exists(name: String) = withContext(ioDispatcher) { dao.exists(name) }

    override suspend fun delete(ids: Collection<Int>) = scope.async(ioDispatcher) {
        dao.delete(ids)
    }.await()

    override suspend fun deleteAll() = scope.async(ioDispatcher) {
        dao.deleteAllExcept(DefaultUserLocation.value.id)
    }.await()

    override fun newPagingSelect() = pagingProvider.newPagingFlowSelect()
    override fun newPagingDelete() = pagingProvider.newPagingFlowDelete()
}