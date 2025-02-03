package com.vladislaviliev.newair.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.screens.home.locationPicker.addLocationPickerDestination
import com.vladislaviliev.newair.screens.home.locationPicker.navigateToLocationPicker
import com.vladislaviliev.newair.screens.home.locationPicker.onLocationPickerDismissRequest
import com.vladislaviliev.newair.screens.home.screen.ScreenRoute
import com.vladislaviliev.newair.screens.home.screen.addScreenDestination
import com.vladislaviliev.newair.screens.map.addLocation.addAddLocationGraph
import com.vladislaviliev.newair.screens.map.addLocation.navigateToAddLocationGraph
import kotlinx.serialization.Serializable

@Serializable
object HomeGraphRoute

fun NavGraphBuilder.addHomeGraph(controller: NavController) {
    navigation<HomeGraphRoute>(ScreenRoute, builder = { addDestinations(controller) })
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addAddLocationGraph(controller)
    addScreenDestination(
        controller::navigateToAddLocationGraph, controller::navigateToLocationPicker,
    )
    addLocationPickerDestination(controller::onLocationPickerDismissRequest)
}