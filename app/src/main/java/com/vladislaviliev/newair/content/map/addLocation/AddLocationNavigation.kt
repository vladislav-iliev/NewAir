package com.vladislaviliev.newair.content.map.addLocation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AddLocationRoute

fun NavGraphBuilder.addAddLocationDestination(onAddLocationClick: (Double, Double) -> Unit) {
    composable<AddLocationRoute>(content = { Content(onAddLocationClick) })
}

@Composable
private fun Content(onAddLocationClick: (Double, Double) -> Unit) {
    AddLocationScreen(onAddLocationClick)
}