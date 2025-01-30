package com.vladislaviliev.newair.content.settings.aboutDialog

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object AboutDialogRoute

fun NavGraphBuilder.addAboutDialogDestination(onDismissRequest: () -> Unit) {
    dialog<AboutDialogRoute>(content = { Content(onDismissRequest) })
}

@Composable
private fun Content(onDismissed: () -> Unit) {
    AboutDialog(onDismissed)
}

fun NavController.navigateToAboutDialog() {
    navigate(AboutDialogRoute)
}

fun NavController.onAboutDialogDismissRequest() {
    popBackStack()
}