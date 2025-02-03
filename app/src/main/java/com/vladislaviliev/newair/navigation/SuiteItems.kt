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
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import com.vladislaviliev.newair.screens.graph.GraphRoute
import com.vladislaviliev.newair.screens.home.HomeGraphRoute
import com.vladislaviliev.newair.screens.map.reading.ReadingMapRoute
import com.vladislaviliev.newair.screens.settings.SettingsGraphRoute

fun NavigationSuiteScope.suiteItems(controller: NavController, currentEntry: NavBackStackEntry?) {
    val items = listOf(
        RouteItem("Map", Icons.Default.Map, ReadingMapRoute),
        RouteItem("Home", Icons.Default.Home, HomeGraphRoute),
        RouteItem("Graph", Icons.Default.BarChart, GraphRoute),
        RouteItem("Settings", Icons.Default.Settings, SettingsGraphRoute),
    )
    items.forEach { item(controller, currentEntry?.destination, it) }
}

private fun NavigationSuiteScope.item(
    controller: NavController,
    currentDestination: NavDestination?,
    item: RouteItem<Any>,
    modifier: Modifier = Modifier
) {

    val isSelected =
        true == currentDestination?.hierarchy?.any { it.route == item.route::class.qualifiedName }

    val navOptions =
        { b: NavOptionsBuilder -> b.popUpTo(controller.graph.findStartDestination().id) }

    val onClick = if (isSelected) {
        {}
    } else {
        { controller.navigate(item.route, builder = navOptions) }
    }

    val icon = @Composable { Icon(item.icon, contentDescription = item.name) }
    val label = @Composable { Text(item.name) }
    item(isSelected, onClick, icon, modifier, label = label)
}
