package com.vladislaviliev.newair

import com.vladislaviliev.air.readings.downloader.Downloader
import com.vladislaviliev.air.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.air.readings.testing.InMemoryHistoryDao
import com.vladislaviliev.air.readings.testing.InMemoryLiveDao
import com.vladislaviliev.air.readings.testing.InMemoryMetadataDao
import com.vladislaviliev.air.user.location.UserLocation
import com.vladislaviliev.air.user.location.UserLocationsRepository
import com.vladislaviliev.air.user.preferences.PreferencesRepository
import com.vladislaviliev.air.user.testing.InMemoryPreferencesDao
import com.vladislaviliev.air.user.testing.InMemoryUserLocationDao
import com.vladislaviliev.newair.screens.home.CITY_NAME
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

object CommonFunctions {

    val city = UserLocation(1, CITY_NAME, 0.0, 0.0)

    fun getDaoCollection(cityId: Int) = TestDaoCollection(
        InMemoryUserLocationDao(),
        InMemoryLiveDao(),
        InMemoryHistoryDao(),
        InMemoryMetadataDao(),
        InMemoryPreferencesDao(cityId)
    )

    fun getRepoCollection(scope: CoroutineScope, cityId: Int = city.id): TestRepoCollection {
        val dispatcher = scope.coroutineContext[CoroutineDispatcher]!!
        val dao = getDaoCollection(cityId)
        return TestRepoCollection(
            UserLocationsRepository(scope, dispatcher, dao.locations, cityId),
            ResponseRepository(
                scope,
                dispatcher,
                Downloader(),
                dao.live,
                dao.history,
                dao.metadata
            ),
            PreferencesRepository(scope, dispatcher, dao.preferences)
        )
    }
}