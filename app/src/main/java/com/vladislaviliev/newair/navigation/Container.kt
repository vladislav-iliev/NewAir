package com.vladislaviliev.newair.navigation

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vladislaviliev.newair.screens.home.HomeGraphRoute

@Composable
fun Container(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val buttons: NavigationSuiteScope.() -> Unit = { addButtons(navController, currentEntry) }
    NavigationSuiteScaffold(buttons, modifier) { ScreenContainer(navController) }
}

@Composable
private fun ScreenContainer(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, HomeGraphRoute, modifier, route = AppGraphRoute::class) {
        addAppGraphDestinations(navController)
    }
}