package com.vladislaviliev.newair.screens.map.addLocation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
private data class Route(val latitude: String, val longitude: String)

fun NavGraphBuilder.addDialogDestination(onDismissRequest: () -> Unit) {
    dialog<Route>(content = { entry -> Content(entry, onDismissRequest) })
}

@Composable
private fun Content(backStackEntry: NavBackStackEntry, onDismissRequest: () -> Unit) {
    val route = backStackEntry.toRoute<Route>()
    val lat = route.latitude.toDouble()
    val lng = route.longitude.toDouble()

    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAddLocationClick = { name: String -> viewModel.onAddLocationClick(name, lat, lng) }

    Dialog(state, onAddLocationClick, onDismissRequest)
}

fun NavController.navigateToAddLocationDialog(latitude: Double, longitude: Double) {
    navigate(Route(latitude.toString(), longitude.toString()))
}

fun NavController.onAddLocationDialogDismissRequest() {
    popBackStack()
}