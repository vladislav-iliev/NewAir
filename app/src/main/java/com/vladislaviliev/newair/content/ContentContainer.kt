package com.vladislaviliev.newair.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vladislaviliev.newair.AppGraphRoute
import com.vladislaviliev.newair.addAppGraphDestinations
import com.vladislaviliev.newair.content.home.HomeGraphRoute

@Composable
fun ContentContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        HomeGraphRoute,
        modifier,
        route = AppGraphRoute::class,
        builder = { addAppGraphDestinations(navController) }
    )
}