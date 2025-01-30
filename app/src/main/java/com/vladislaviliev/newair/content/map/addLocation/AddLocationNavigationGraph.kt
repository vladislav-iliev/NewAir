package com.vladislaviliev.newair.content.map.addLocation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.content.map.addLocation.dialog.addAddLocationDialogDestination
import com.vladislaviliev.newair.content.map.addLocation.dialog.navigateToAddLocationDialog
import com.vladislaviliev.newair.content.map.addLocation.dialog.onAddLocationDialogDismissRequest
import kotlinx.serialization.Serializable

@Serializable
object AddLocationGraphRoute

fun NavGraphBuilder.addAddLocationGraph(controller: NavController) {
    navigation<AddLocationGraphRoute>(AddLocationRoute, builder = { addDestinations(controller) })
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addAddLocationDestination(controller::navigateToAddLocationDialog)
    addAddLocationDialogDestination(controller::onAddLocationDialogDismissRequest)
}

fun NavController.navigateToAddLocationGraph() {
    navigate(AddLocationGraphRoute)
}