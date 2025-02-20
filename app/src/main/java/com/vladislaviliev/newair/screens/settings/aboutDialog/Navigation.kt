package com.vladislaviliev.newair.screens.settings.aboutDialog

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
private object Route

fun NavGraphBuilder.addAboutDialogDestination(onDismissRequest: () -> Unit) {
    dialog<Route> { Content(onDismissRequest) }
}

@Composable
private fun Content(onDismissed: () -> Unit) {
    AboutDialog(onDismissed)
}

fun NavController.navigateToAboutDialog() {
    navigate(Route)
}

fun NavController.onAboutDialogDismissRequest() {
    popBackStack()
}