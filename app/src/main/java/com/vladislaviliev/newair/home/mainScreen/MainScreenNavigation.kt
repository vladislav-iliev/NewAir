package com.vladislaviliev.newair.home.mainScreen

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.newair.AppState
import com.vladislaviliev.newair.home.mainScreen.state.HomeScreenState
import com.vladislaviliev.newair.home.mainScreen.uiComponents.HomeScreen
import com.vladislaviliev.newair.home.mainScreen.uiComponents.TopAppBar
import com.vladislaviliev.newair.readings.calculations.Health
import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute

fun NavGraphBuilder.addMainScreenNavDestination(
    onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit,
) {
    composable<MainScreenRoute>(
        content = { Content(onAddNewLocationClick, onLocationPickerClick) }
    )
}

@Composable
private fun Content(onAddNewLocationClick: () -> Unit, onLocationPickerClick: () -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()
    SetTopAppBar(uiState, onAddNewLocationClick, viewModel::onRefreshClick)
    HomeScreen(uiState, onLocationPickerClick)
}

@Composable
private fun SetTopAppBar(
    uiState: HomeScreenState, onAddNewLocationClick: () -> Unit, onRefreshClick: () -> Unit
) {
    val backgroundColor = Health.checkUnspecifiedBackgroundColor(uiState.backgroundColor)
    val contentColor = Health.checkUnspecifiedContentColor(uiState.contentColor)

    val appState = viewModel<AppState>(LocalActivity.current as ComponentActivity)
    appState.setTopAppBar {
        TopAppBar(onAddNewLocationClick, onRefreshClick, backgroundColor, contentColor)
    }
}