package com.vladislaviliev.newair

import androidx.paging.testing.asSnapshot
import com.vladislaviliev.newair.dao.InMemorySettingsDao
import com.vladislaviliev.newair.dao.InMemoryUserLocationDao
import com.vladislaviliev.newair.screens.home.locationPicker.ViewModel
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Model
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test


class PagingTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun hasCity() = runTest {
        val scope = this
        val dispatcher = coroutineContext[CoroutineDispatcher]!!

        val locationsRepository =
            UserLocationsRepository(scope, dispatcher, InMemoryUserLocationDao())
        locationsRepository.addInitial()
        locationsRepository.deleteAllExceptCity()
        val settingsRepository = SettingsRepository(scope, dispatcher, InMemorySettingsDao())

        val snapshot = ViewModel(locationsRepository, settingsRepository).pagingFlow.asSnapshot()

        val city = City.value
        val cityChar = city.name.first().uppercaseChar()
        assertNotNull(snapshot.singleOrNull(Model.Header(cityChar)::equals))
        assertNotNull(snapshot.singleOrNull(Model.Location(city.id, city.name)::equals))
    }
}