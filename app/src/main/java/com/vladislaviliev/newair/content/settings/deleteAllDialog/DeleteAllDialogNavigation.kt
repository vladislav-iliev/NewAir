package com.vladislaviliev.newair.content.settings.deleteAllDialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val message by viewModel.message.collectAsStateWithLifecycle()
    DeleteAllDialog(message, viewModel::deleteAll, onDismissRequest)
}

fun NavController.navigateToDeleteAllDialog() {
    navigate(DeleteAllDialogRoute)
}

fun NavController.onDeleteAllDialogDismissRequest() {
    popBackStack()
}