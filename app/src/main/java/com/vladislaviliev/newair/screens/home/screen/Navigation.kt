package com.vladislaviliev.newair.screens.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.newair.screens.home.screen.uiComponents.Screen
import kotlinx.serialization.Serializable

@Serializable
object ScreenRoute

fun NavGraphBuilder.addScreenDestination(
    onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit,
) {
    composable<ScreenRoute>(content = { Content(onAddNewLocationClick, onLocationPickerClick) })
}

@Composable
private fun Content(onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    Screen(onAddNewLocationClick, viewModel::onRefreshClick, onLocationPickerClick, state)
}