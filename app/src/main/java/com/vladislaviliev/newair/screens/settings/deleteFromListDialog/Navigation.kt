package com.vladislaviliev.newair.screens.settings.deleteFromListDialog

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.serialization.Serializable

@Serializable
private object Route

fun NavGraphBuilder.addDeleteFromListDestination(onDismissRequest: () -> Unit) {
    dialog<Route>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()
    val items = viewModel.pagingFlow.collectAsLazyPagingItems()
    Dialog(items, viewModel::delete, onDismissRequest)
}

fun NavController.navigateToDeleteFromList() {
    navigate(Route)
}

fun NavController.onDeleteFromListDismissRequest() {
    popBackStack()
}