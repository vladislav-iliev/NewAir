package com.vladislaviliev.newair.screens.settings.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ScreenRoute

fun NavGraphBuilder.addSettingsScreenDestination(
    onDeleteLocationsClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    onAboutClicked: () -> Unit,
) {
    composable<ScreenRoute> {
        Content(onDeleteLocationsClicked, onDeleteAllClicked, onAboutClicked)
    }
}

@Composable
private fun Content(
    onDeleteLocationsClicked: () -> Unit, onDeleteAllClicked: () -> Unit, onAboutClicked: () -> Unit
) {
    Screen(onDeleteLocationsClicked, onDeleteAllClicked, onAboutClicked)
}