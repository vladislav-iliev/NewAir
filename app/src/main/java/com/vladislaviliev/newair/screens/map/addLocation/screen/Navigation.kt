package com.vladislaviliev.newair.screens.map.addLocation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AddLocationRoute

fun NavGraphBuilder.addScreenDestination(onAddLocationClick: (Double, Double) -> Unit) {
    composable<AddLocationRoute> { Content(onAddLocationClick) }
}

@Composable
private fun Content(onAddLocationClick: (Double, Double) -> Unit) {
    Screen(onAddLocationClick)
}