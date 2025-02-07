package com.vladislaviliev.newair

import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.SampleLocations
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Transformer
import java.util.concurrent.atomic.AtomicInteger

//TODO: Add delays to test background work after navigating away
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
        add(City.value)
        SampleLocations.locations.forEach { add(it) }
    }

    override suspend fun getLastId() = lastId.get()

    override suspend fun exists(name: String) = locations.values.any { it.name == name }

    override suspend fun delete(ids: Collection<Int>) {
        ids.forEach { locations.remove(it) }
    }

    override suspend fun deleteAllExceptCity() {
        delete(setOf(City.value.id))
    }

    override fun newPagingSelect() = Transformer.flowOf(TestPagingSource(locations.values.toList()))

    override fun newPagingDelete() =
        Transformer.flowOf(TestPagingSource(locations.values.filter { it != City.value }.toList()))
}