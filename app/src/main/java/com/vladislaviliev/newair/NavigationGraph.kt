package com.vladislaviliev.newair

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.vladislaviliev.newair.screens.graph.addGraphDestination
import com.vladislaviliev.newair.screens.home.addHomeGraph
import com.vladislaviliev.newair.screens.map.reading.addReadingMapDestination
import com.vladislaviliev.newair.screens.settings.addSettingsGraph
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addHomeGraph(controller)
    addReadingMapDestination()
    addGraphDestination()
    addSettingsGraph(controller)
}