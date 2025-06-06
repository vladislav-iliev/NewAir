package com.vladislaviliev.newair.screens.home.locationPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object Route

fun NavGraphBuilder.addLocationPickerDestination(onDismissRequest: () -> Unit) {
    dialog<Route> { Content(onDismissRequest) }
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()

    val hasSelected by viewModel.hasSelected.collectAsStateWithLifecycle()
    if (hasSelected) {
        onDismissRequest()
        return
    }

    LocationPicker(
        viewModel.pagingFlow,
        viewModel.preselectId,
        viewModel::onLocationSelected,
        onDismissRequest,
    )
}

fun NavController.navigateToLocationPicker() {
    navigate(Route)
}

fun NavController.onLocationPickerDismissRequest() {
    popBackStack()
}