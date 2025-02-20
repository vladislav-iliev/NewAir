package com.vladislaviliev.newair.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.vladislaviliev.newair.screens.graph.GraphRoute
import com.vladislaviliev.newair.screens.home.HomeGraphRoute
import com.vladislaviliev.newair.screens.map.reading.ReadingMapRoute
import com.vladislaviliev.newair.screens.settings.SettingsGraphRoute

private data class Button<out T>(val label: String, val icon: ImageVector, val route: T)

fun NavigationSuiteScope.addButtons(controller: NavController, currentEntry: NavBackStackEntry?) {
    listOf(
        Button("Map", Icons.Default.Map, ReadingMapRoute),
        Button("Home", Icons.Default.Home, HomeGraphRoute),
        Button("Graph", Icons.Default.BarChart, GraphRoute),
        Button("Settings", Icons.Default.Settings, SettingsGraphRoute),
    ).forEach { addButton(controller, currentEntry?.destination, it) }
}

private fun NavigationSuiteScope.addButton(
    controller: NavController, currentDestination: NavDestination?, item: Button<Any>,
) {
    val isSelected = true == currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) }

    val onClick = if (isSelected) {
        {}
    } else {
        { controller.navigateTo(item.route) }
    }

    val icon = @Composable { Icon(item.icon, contentDescription = item.label) }
    val label = @Composable { Text(item.label) }
    item(isSelected, onClick, icon, label = label)
}

private fun NavController.navigateTo(route: Any) {
    val popTo = HomeGraphRoute.startDestination
    if (route == HomeGraphRoute) {
        popBackStack(popTo, false)
        return
    }
    navigate(route) { popUpTo(popTo) }
}
