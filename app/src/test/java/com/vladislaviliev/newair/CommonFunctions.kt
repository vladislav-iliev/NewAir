package com.vladislaviliev.newair

import com.vladislaviliev.newair.dao.InMemoryHistoryDao
import com.vladislaviliev.newair.dao.InMemoryLiveDao
import com.vladislaviliev.newair.dao.InMemoryMetadataDao
import com.vladislaviliev.newair.dao.InMemorySettingsDao
import com.vladislaviliev.newair.dao.InMemoryUserLocationDao
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.screens.home.screen.uiComponents.cityNamePlaceholder
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.settings.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

object CommonFunctions {

    val city = UserLocation(1, cityNamePlaceholder, 0.0, 0.0)

    fun getDaoCollection(cityId: Int) = TestDaoCollection(
        InMemoryUserLocationDao(),
        InMemoryLiveDao(),
        InMemoryHistoryDao(),
        InMemoryMetadataDao(),
        InMemorySettingsDao(cityId)
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
            SettingsRepository(scope, dispatcher, dao.settings)
        )
    }
}