package com.vladislaviliev.newair

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.vladislaviliev.newair.content.graph.addGraphDestination
import com.vladislaviliev.newair.content.home.addHomeGraph
import com.vladislaviliev.newair.content.map.readingMap.addReadingDisplayDestination
import com.vladislaviliev.newair.content.settings.addSettingsGraph
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addHomeGraph(controller)
    addReadingDisplayDestination()
    addGraphDestination()
    addSettingsGraph(controller)
}