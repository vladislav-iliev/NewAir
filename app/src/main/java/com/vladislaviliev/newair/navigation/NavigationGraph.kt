package com.vladislaviliev.newair.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import com.vladislaviliev.newair.screens.graph.addGraphDestination
import com.vladislaviliev.newair.screens.home.HomeGraphRoute
import com.vladislaviliev.newair.screens.home.addHomeGraph
import com.vladislaviliev.newair.screens.map.reading.addReadingMapDestination
import com.vladislaviliev.newair.screens.settings.addSettingsGraph
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun createAppGraph(controller: NavController) = controller
    .createGraph(HomeGraphRoute, AppGraphRoute::class) { addAppGraphDestinations(controller) }

private fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addHomeGraph(controller)
    addReadingMapDestination()
    addGraphDestination()
    addSettingsGraph(controller)
}