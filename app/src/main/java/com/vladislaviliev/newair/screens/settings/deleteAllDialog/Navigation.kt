package com.vladislaviliev.newair.screens.settings.deleteAllDialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
private object Route

fun NavGraphBuilder.addDeleteAllDialogDestination(onDismissRequest: () -> Unit) {
    dialog<Route>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissRequest: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()
    val message by viewModel.message.collectAsStateWithLifecycle()
    Dialog(message, viewModel::deleteAll, onDismissRequest)
}

fun NavController.navigateToDeleteAllDialog() {
    navigate(Route)
}

fun NavController.onDeleteAllDialogDismissRequest() {
    popBackStack()
}