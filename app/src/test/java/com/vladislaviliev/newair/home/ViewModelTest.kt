package com.vladislaviliev.newair.home

import com.vladislaviliev.newair.CommonFunctions
import com.vladislaviliev.newair.MainDispatcherRule
import com.vladislaviliev.newair.screens.home.screen.ViewModel
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    @Test
    fun on_app_startup_display_only_loading() = runTest {
        val id = CommonFunctions.city.id

        val (locationsRepo, responseRepo, settingsRepo) =
            CommonFunctions.getRepoCollection(this, id)

        val vm = ViewModel(id, locationsRepo, responseRepo, settingsRepo)
        val state = vm.screenState.first()
        Assert.assertEquals(Loading.value, state)
    }

    @Test
    fun on_clean_install_city_is_selected() = runTest {
        val (id, name, lat, lon) = CommonFunctions.city

        val (locationsRepo, responseRepo, settingsRepo) =
            CommonFunctions.getRepoCollection(this, id)

        locationsRepo.add(name, lat, lon)

        val vm = ViewModel(id, locationsRepo, responseRepo, settingsRepo)
        val firstTwoEmissions = vm.screenState.take(2).toList()
        Assert.assertEquals(name, firstTwoEmissions[1].location)
    }
}