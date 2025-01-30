package com.vladislaviliev.newair.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.home.locationPicker.addLocationPickerDestination
import com.vladislaviliev.newair.home.locationPicker.navigateToLocationPicker
import com.vladislaviliev.newair.home.locationPicker.onLocationPickerDismissRequest
import com.vladislaviliev.newair.home.mainScreen.MainScreenRoute
import com.vladislaviliev.newair.home.mainScreen.addMainScreenNavDestination
import com.vladislaviliev.newair.map.addLocation.addAddLocationGraph
import com.vladislaviliev.newair.map.addLocation.navigateToAddLocationGraph
import kotlinx.serialization.Serializable

@Serializable
object HomeGraphRoute

fun NavGraphBuilder.addHomeGraph(controller: NavController) {
    navigation<HomeGraphRoute>(MainScreenRoute, builder = { addDestinations(controller) })
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addAddLocationGraph(controller)
    addMainScreenNavDestination(
        controller::navigateToAddLocationGraph,
        controller::navigateToLocationPicker,
    )
    addLocationPickerDestination(controller::onLocationPickerDismissRequest)
}