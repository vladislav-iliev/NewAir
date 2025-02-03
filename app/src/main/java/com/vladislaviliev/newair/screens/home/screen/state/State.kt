package com.vladislaviliev.newair.screens.home.screen.state

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class State(
    val location: String,
    @StringRes val message: Int,
    val errorMessage: String,
    val timestamp: String,
    val pollution: String,
    val temperature: String,
    val humidity: String,
    val backgroundColor: Color,
    val contentColor: Color,
)