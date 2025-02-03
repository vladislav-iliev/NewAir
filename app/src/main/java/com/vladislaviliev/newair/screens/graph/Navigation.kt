package com.vladislaviliev.newair.screens.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.newair.screens.graph.uiComponents.Screen
import kotlinx.serialization.Serializable

@Serializable
object GraphRoute

fun NavGraphBuilder.addGraphDestination() {
    composable<GraphRoute>(content = { Content() })
}

@Composable
private fun Content() {
    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Screen(viewModel::onRefreshClick, state)
}