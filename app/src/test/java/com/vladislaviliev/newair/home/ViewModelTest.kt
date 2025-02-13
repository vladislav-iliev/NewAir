package com.vladislaviliev.newair.home

import com.vladislaviliev.newair.MainDispatcherRule
import com.vladislaviliev.newair.dao.InMemoryHistoryDao
import com.vladislaviliev.newair.dao.InMemoryLiveDao
import com.vladislaviliev.newair.dao.InMemoryMetadataDao
import com.vladislaviliev.newair.dao.InMemorySettingsDao
import com.vladislaviliev.newair.dao.InMemoryUserLocationDao
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.readings.history.HistoryDao
import com.vladislaviliev.newair.readings.live.LiveDao
import com.vladislaviliev.newair.screens.home.screen.ViewModel
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.settings.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.vladislaviliev.newair.readings.downloader.metadata.Dao as MetadataDao
import com.vladislaviliev.newair.user.location.Dao as UserLocationDao
import com.vladislaviliev.newair.user.settings.Dao as SettingsDao

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var locationsDao: UserLocationDao
    private lateinit var liveDao: LiveDao
    private lateinit var historyDao: HistoryDao
    private lateinit var metadataDao: MetadataDao
    private lateinit var settingsDao: SettingsDao

    private fun getLocationsRepo(scope: CoroutineScope, dispatcher: CoroutineDispatcher) =
        UserLocationsRepository(scope, dispatcher, locationsDao)

    private fun getResponseRepo(scope: CoroutineScope, dispatcher: CoroutineDispatcher) =
        ResponseRepository(scope, dispatcher, Downloader(), liveDao, historyDao, metadataDao)

    private fun getSettingsRepo(scope: CoroutineScope, dispatcher: CoroutineDispatcher) =
        SettingsRepository(scope, dispatcher, settingsDao)

    private fun TestScope.getCoroutineElements() =
        Pair(this, coroutineContext[CoroutineDispatcher]!!)

    @Before
    fun before() {
        locationsDao = InMemoryUserLocationDao()
        liveDao = InMemoryLiveDao()
        historyDao = InMemoryHistoryDao()
        metadataDao = InMemoryMetadataDao()
        settingsDao = InMemorySettingsDao()
    }

    @Test
    fun on_app_startup_display_only_loading() = runTest {
        val (scope, dispatcher) = getCoroutineElements()

        val vm = ViewModel(
            getLocationsRepo(scope, dispatcher),
            getResponseRepo(scope, dispatcher),
            getSettingsRepo(scope, dispatcher)
        )

        val state = vm.screenState.first()
        Assert.assertEquals(Loading.value, state)
    }

    @Test
    fun on_clean_install_city_is_selected() = runTest {
        val (scope, dispatcher) = getCoroutineElements()
        locationsDao.upsert(City)

        val vm = ViewModel(
            getLocationsRepo(scope, dispatcher),
            getResponseRepo(scope, dispatcher),
            getSettingsRepo(scope, dispatcher)
        )

        val firstTwoEmissions = vm.screenState.take(2).toList()
        Assert.assertEquals(City.name, firstTwoEmissions[1].location)
    }
}