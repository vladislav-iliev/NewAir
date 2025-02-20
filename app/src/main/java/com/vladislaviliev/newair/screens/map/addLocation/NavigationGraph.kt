package com.vladislaviliev.newair.screens.map.addLocation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.screens.map.addLocation.dialog.addDialogDestination
import com.vladislaviliev.newair.screens.map.addLocation.dialog.navigateToAddLocationDialog
import com.vladislaviliev.newair.screens.map.addLocation.dialog.onAddLocationDialogDismissRequest
import com.vladislaviliev.newair.screens.map.addLocation.screen.AddLocationRoute
import com.vladislaviliev.newair.screens.map.addLocation.screen.addScreenDestination
import kotlinx.serialization.Serializable

@Serializable
private object AddLocationGraphRoute

fun NavGraphBuilder.addAddLocationGraph(controller: NavController) {
    navigation<AddLocationGraphRoute>(AddLocationRoute) { addDestinations(controller) }
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addScreenDestination(controller::navigateToAddLocationDialog)
    addDialogDestination(controller::onAddLocationDialogDismissRequest)
}

fun NavController.navigateToAddLocationGraph() {
    navigate(AddLocationGraphRoute)
}