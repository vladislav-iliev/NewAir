package com.vladislaviliev.newair.content.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object GraphRoute

fun NavGraphBuilder.addGraphDestination() {
    composable<GraphRoute>(content = { Content() })
}

@Composable
private fun Content() {
    val viewModel = hiltViewModel<GraphViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    GraphScreen(state, viewModel::onRefreshClick)
}