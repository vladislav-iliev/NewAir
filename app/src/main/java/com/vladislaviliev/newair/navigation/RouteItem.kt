package com.vladislaviliev.newair.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class RouteItem<out T : Any>(val name: String, val icon: ImageVector, val route: T)