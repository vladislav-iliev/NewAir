package com.vladislaviliev.newair.content.home.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.newair.content.home.mainScreen.uiComponents.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute

fun NavGraphBuilder.addMainScreenNavDestination(
    onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit,
) {
    composable<MainScreenRoute>(content = { Content(onAddNewLocationClick, onLocationPickerClick) })
}

@Composable
private fun Content(onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()
    HomeScreen(uiState, onAddNewLocationClick, viewModel::onRefreshClick, onLocationPickerClick)
}