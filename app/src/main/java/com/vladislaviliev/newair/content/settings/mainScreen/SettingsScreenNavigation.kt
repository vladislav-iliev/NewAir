package com.vladislaviliev.newair.content.settings.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreenRoute

fun NavGraphBuilder.addSettingsScreenDestination(
    onDeleteLocationsClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    onAboutClicked: () -> Unit,
) {
    composable<SettingsScreenRoute>(
        content = { Content(onDeleteLocationsClicked, onDeleteAllClicked, onAboutClicked) }
    )
}

@Composable
private fun Content(
    onDeleteLocationsClicked: () -> Unit, onDeleteAllClicked: () -> Unit, onAboutClicked: () -> Unit
) {
    val viewModel = hiltViewModel<SettingsScreenViewModel>()
    val isColorBlind by viewModel.colorBlind.collectAsStateWithLifecycle()
    SettingsScreen(
        isColorBlind,
        viewModel::onColorBlindChanged,
        onDeleteLocationsClicked,
        onDeleteAllClicked,
        onAboutClicked,
    )
}