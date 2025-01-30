package com.vladislaviliev.newair.settings.deleteAllDialog

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object DeleteAllDialogRoute

fun NavGraphBuilder.addDeleteAllDialogDestination(onDismissRequest: () -> Unit) {
    dialog<DeleteAllDialogRoute>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<DeleteAllViewModel>()
    val onConfirm = { viewModel.deleteAll(); onDismissRequest() }
    DeleteAllDialog(onConfirm, onDismissRequest)
}

fun NavController.navigateToDeleteAllDialog() {
    navigate(DeleteAllDialogRoute)
}

fun NavController.onDeleteAllDialogDismissRequest() {
    popBackStack()
}