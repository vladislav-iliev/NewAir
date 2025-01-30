package com.vladislaviliev.newair.content.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.content.home.locationPicker.addLocationPickerDestination
import com.vladislaviliev.newair.content.home.locationPicker.navigateToLocationPicker
import com.vladislaviliev.newair.content.home.locationPicker.onLocationPickerDismissRequest
import com.vladislaviliev.newair.content.home.mainScreen.MainScreenRoute
import com.vladislaviliev.newair.content.home.mainScreen.addMainScreenNavDestination
import com.vladislaviliev.newair.content.map.addLocation.addAddLocationGraph
import com.vladislaviliev.newair.content.map.addLocation.navigateToAddLocationGraph
import kotlinx.serialization.Serializable

@Serializable
object HomeGraphRoute

fun NavGraphBuilder.addHomeGraph(controller: NavController) {
    navigation<HomeGraphRoute>(MainScreenRoute, builder = { addDestinations(controller) })
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addAddLocationGraph(controller)
    addMainScreenNavDestination(
        controller::navigateToAddLocationGraph, controller::navigateToLocationPicker,
    )
    addLocationPickerDestination(controller::onLocationPickerDismissRequest)
}