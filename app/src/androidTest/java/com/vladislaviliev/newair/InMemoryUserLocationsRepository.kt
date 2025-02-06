package com.vladislaviliev.newair

import androidx.paging.PagingData
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import com.vladislaviliev.newair.user.location.SampleLocations
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Model
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicInteger

class InMemoryUserLocationsRepository : UserLocationsRepository {

    private var lastId = AtomicInteger(0)
    private val locations = sortedMapOf<Int, UserLocation>(Int::compareTo)

    override suspend fun get(id: Int) = locations[id] ?: throw NoSuchElementException()

    override suspend fun add(name: String, lat: Double, lng: Double) {
        val key = lastId.incrementAndGet()
        locations[key] = UserLocation(key, name, lat, lng)
    }

    private fun add(userLocation: UserLocation) {
        val key = if (userLocation.id == 0) lastId.incrementAndGet() else userLocation.id
        locations[key] = userLocation.copy(id = key)
    }

    override suspend fun addInitial() {
        add(DefaultUserLocation.value)
        SampleLocations.locations.forEach { add(it) }
    }

    override suspend fun getLastId() = lastId.get()

    override suspend fun exists(name: String) = locations.values.any { it.name == name }

    override suspend fun delete(ids: Collection<Int>) {
        locations.keys.removeAll(ids)
    }

    override suspend fun deleteAll() {
        locations.clear()
    }

    override fun newPagingSelect(): Flow<PagingData<Model>> = TODO("Not yet implemented")
    override fun newPagingDelete(): Flow<PagingData<Model>> = TODO("Not yet implemented")
}