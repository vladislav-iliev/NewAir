package com.vladislaviliev.newair.navigationBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vladislaviliev.newair.graph.GraphRoute
import com.vladislaviliev.newair.home.HomeGraphRoute
import com.vladislaviliev.newair.map.readingDisplay.ReadingDisplayRoute
import com.vladislaviliev.newair.settings.SettingsGraphRoute

@Composable
fun NavBar(controller: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        RouteItem("Map", Icons.Default.Map, ReadingDisplayRoute),
        RouteItem("Home", Icons.Default.Home, HomeGraphRoute),
        RouteItem("Graph", Icons.Default.BarChart, GraphRoute),
        RouteItem("Settings", Icons.Default.Settings, SettingsGraphRoute),
    )
    NavigationBar(modifier, content = { Row(controller, items) })
}

@Composable
private fun RowScope.Row(controller: NavController, items: List<RouteItem<Any>>) {
    val currentEntry by controller.currentBackStackEntryAsState()
    if (currentEntry == null) return
    items.forEach { RowItem(controller, currentEntry!!.destination, it) }
}

@Composable
private fun RowScope.RowItem(
    controller: NavController,
    currentDestination: NavDestination,
    item: RouteItem<Any>,
    modifier: Modifier = Modifier
) {

    val isSelected = true == currentDestination.hierarchy.any {
        it.route == item.route::class.qualifiedName
    }

    val navOptions: NavOptionsBuilder.() -> Unit = {
        popUpTo(controller.graph.findStartDestination().id)
        launchSingleTop = true
    }
    val onClick = { controller.navigate(item.route, builder = navOptions) }

    val icon = @Composable { Icon(item.icon, contentDescription = item.name) }
    val label = @Composable { Text(item.name) }
    NavigationBarItem(isSelected, onClick, icon, modifier, label = label)
}
