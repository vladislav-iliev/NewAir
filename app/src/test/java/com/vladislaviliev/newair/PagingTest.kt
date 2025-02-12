package com.vladislaviliev.newair

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.vladislaviliev.newair.dao.InMemorySettingsDao
import com.vladislaviliev.newair.dao.InMemoryUserLocationDao
import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Model
import com.vladislaviliev.newair.user.settings.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import com.vladislaviliev.newair.screens.home.locationPicker.ViewModel as SelectViewModel
import com.vladislaviliev.newair.screens.settings.deleteFromListDialog.ViewModel as DeleteViewModel


@OptIn(ExperimentalCoroutinesApi::class)
class PagingTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private fun pagingConfig(): PagingConfig {
        val pageSize = 20
        val prefetchDistance = 10
        return PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize * 2,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = false,
        )
    }

    private fun getLocationsRepo(scope: CoroutineScope, dispatcher: CoroutineDispatcher) =
        UserLocationsRepository(scope, dispatcher, InMemoryUserLocationDao())

    private suspend fun UserLocationsRepository.addSampleLocations() {
        SampleLocations.locations.forEach { add(it.name, it.latitude, it.longitude) }
    }

    private fun getSettingsRepo(scope: CoroutineScope, dispatcher: CoroutineDispatcher) =
        SettingsRepository(scope, dispatcher, InMemorySettingsDao())

    private suspend fun has_header(has: Boolean, flow: Flow<PagingData<Model>>, char: Char) {
        val model = Model.Header(char)
        val snapshot = flow.asSnapshot { appendScrollWhile { model != it } }
        val foundItem = snapshot.singleOrNull(model::equals)

        if (has) Assert.assertNotNull(foundItem)
        else Assert.assertNull(foundItem)
    }

    private suspend fun has_location(
        has: Boolean,
        flow: Flow<PagingData<Model>>,
        location: UserLocation
    ) {
        val model = Model.Location(location.id, location.name)
        val snapshot = flow.asSnapshot { appendScrollWhile { model != it } }
        val foundItem = snapshot.singleOrNull(model::equals)

        if (has) Assert.assertNotNull(foundItem)
        else Assert.assertNull(foundItem)
    }

    @Test
    fun select_picker_has_all_within_repository() = runTest {
        val scope = this
        val dispatcher = coroutineContext[CoroutineDispatcher]!!

        val locationsRepo =
            getLocationsRepo(scope, dispatcher).apply { addInitial(); addSampleLocations() }

        val vm = SelectViewModel(locationsRepo, getSettingsRepo(scope, dispatcher), pagingConfig())

        SampleLocations.locations.forEach {
            has_header(true, vm.pagingFlow, it.name.first().uppercaseChar())
            has_location(true, vm.pagingFlow, it)
        }
        advanceUntilIdle()
    }

    @Test
    fun delete_picker_has_all_within_repository_except_city() = runTest {
        val scope = this
        val dispatcher = coroutineContext[CoroutineDispatcher]!!

        val locationsRepo =
            getLocationsRepo(scope, dispatcher).apply { addInitial(); addSampleLocations() }

        val vm = DeleteViewModel(locationsRepo, getSettingsRepo(scope, dispatcher), pagingConfig())

        has_location(false, vm.pagingFlow, City.value)
        SampleLocations.locations.forEach {
            has_header(true, vm.pagingFlow, it.name.first().uppercaseChar())
            has_location(true, vm.pagingFlow, it)
        }
        advanceUntilIdle()
    }
}