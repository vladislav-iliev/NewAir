package com.vladislaviliev.newair

import androidx.paging.PagingSource
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationDao
import java.util.concurrent.atomic.AtomicInteger

//TODO: Add delays to test background work after navigating away
class InMemoryUserLocationDao : UserLocationDao {

    private var lastId = AtomicInteger(0)
    private val locations = sortedMapOf<Int, UserLocation>(Int::compareTo)

    override suspend fun get(id: Int) = locations[id] ?: throw NoSuchElementException()

    override suspend fun upsert(userLocation: UserLocation) {
        locations[userLocation.id] = userLocation
    }

    override suspend fun upsert(userLocations: Collection<UserLocation>) {
        userLocations.forEach { upsert(it) }
    }

    override suspend fun exists(name: String) = locations.values.any { it.name == name }

    override suspend fun getLastId() = lastId.get()

    override suspend fun delete(ids: Collection<Int>) {
        ids.forEach { locations.remove(it) }
    }

    override suspend fun deleteAllExcept(id: Int) {
        locations.keys.filter { it != id }.forEach { locations.remove(it) }
    }

    override fun newPagingSource(excluding: Int): PagingSource<Int, UserLocation> {
        return TestPagingSource(locations.values.filter { it.id != excluding }.toList())
    }
}