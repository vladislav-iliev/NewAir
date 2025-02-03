package com.vladislaviliev.newair.screens.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    composable<ScreenRoute>(
        content = { Content(onDeleteLocationsClicked, onDeleteAllClicked, onAboutClicked) }
    )
}

@Composable
private fun Content(
    onDeleteLocationsClicked: () -> Unit, onDeleteAllClicked: () -> Unit, onAboutClicked: () -> Unit
) {
    val viewModel = hiltViewModel<ViewModel>()
    val isColorBlind by viewModel.colorBlind.collectAsStateWithLifecycle()
    Screen(
        isColorBlind,
        viewModel::onColorBlindChanged,
        onDeleteLocationsClicked,
        onDeleteAllClicked,
        onAboutClicked,
    )
}