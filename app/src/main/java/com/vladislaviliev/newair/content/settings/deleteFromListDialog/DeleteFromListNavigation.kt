package com.vladislaviliev.newair.content.settings.deleteFromListDialog

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.serialization.Serializable

@Serializable
object DeleteFromListRoute

fun NavGraphBuilder.addDeleteFromListDestination(onDismissRequest: () -> Unit) {
    dialog<DeleteFromListRoute>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<DeleteFromListViewModel>()
    val items = viewModel.pagingFlow.collectAsLazyPagingItems()
    DeleteFromListDialog(items, viewModel::delete, onDismissRequest)
}

fun NavController.navigateToDeleteFromList() {
    navigate(DeleteFromListRoute)
}

fun NavController.onDeleteFromListDismissRequest() {
    popBackStack()
}