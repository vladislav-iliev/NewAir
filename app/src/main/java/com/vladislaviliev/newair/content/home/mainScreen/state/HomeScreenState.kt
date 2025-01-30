package com.vladislaviliev.newair.content.home.mainScreen.state

import androidx.compose.ui.graphics.Color

data class HomeScreenState(
    val backgroundColor: Color,
    val contentColor: Color,
    val location: String,
    val pollution: String,
    val message: String,
    val temperature: String,
    val humidity: String,
    val timestamp: String,
)