package com.vladislaviliev.newair.screens.map.reading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.newair.screens.map.reading.uiComponents.Screen
import kotlinx.serialization.Serializable

@Serializable
object ReadingMapRoute

fun NavGraphBuilder.addReadingMapDestination() {
    composable<ReadingMapRoute> { Content() }
}

@Composable
private fun Content() {
    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Screen(viewModel::onRefreshClick, state)
}