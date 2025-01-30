package com.vladislaviliev.newair.map.readingDisplay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ReadingDisplayRoute

fun NavGraphBuilder.addReadingDisplayDestination() {
    composable<ReadingDisplayRoute>(content = { Content() })
}

@Composable
private fun Content() {
    val viewModel = hiltViewModel<ReadingDisplayViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ReadingDisplay(state.isColorBlind, state.readings, viewModel::onRefreshClicked)
}

fun NavController.navigateToReadingDisplay() {
    navigate(ReadingDisplayRoute)
}