package com.vladislaviliev.newair

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vladislaviliev.newair.screens.home.HomeGraphRoute

@Composable
fun ScreenContainer(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        HomeGraphRoute,
        modifier,
        route = AppGraphRoute::class,
        builder = { addAppGraphDestinations(navController) }
    )
}