package com.vladislaviliev.newair

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.vladislaviliev.newair.graph.addGraphDestination
import com.vladislaviliev.newair.home.addHomeGraph
import com.vladislaviliev.newair.map.readingDisplay.addReadingDisplayDestination
import com.vladislaviliev.newair.settings.addSettingsGraph
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addHomeGraph(controller)
    addReadingDisplayDestination()
    addGraphDestination()
    addSettingsGraph(controller)
}