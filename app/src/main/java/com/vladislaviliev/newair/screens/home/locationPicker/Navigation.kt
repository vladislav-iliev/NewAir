package com.vladislaviliev.newair.screens.home.locationPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.paging.compose.collectAsLazyPagingItems
import com.vladislaviliev.newair.user.location.City
import kotlinx.serialization.Serializable

@Serializable
object Route

fun NavGraphBuilder.addLocationPickerDestination(onDismissRequest: () -> Unit) {
    dialog<Route>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()

    val hasSelected by viewModel.hasSelected.collectAsStateWithLifecycle()
    if (hasSelected) {
        onDismissRequest()
        return
    }

    val items = viewModel.pagingFlow.collectAsLazyPagingItems()
    LocationPicker(items, City.value.id, viewModel::onLocationSelected, onDismissRequest)
}

fun NavController.navigateToLocationPicker() {
    navigate(Route)
}

fun NavController.onLocationPickerDismissRequest() {
    popBackStack()
}