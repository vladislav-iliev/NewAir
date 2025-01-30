package com.vladislaviliev.newair

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vladislaviliev.newair.home.HomeGraphRoute

@Composable
fun ScreenContainer(
    paddingValues: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        HomeGraphRoute,
        modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues),
        route = AppGraphRoute::class,
        builder = { addAppGraphDestinations(navController) }
    )
}