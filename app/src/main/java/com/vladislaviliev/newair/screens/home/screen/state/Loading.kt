package com.vladislaviliev.newair.screens.home.screen.state

import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.R

data object Loading {
    val value = State(
        "", R.string.loading, "", "", "", "", "", Color.Unspecified, Color.Unspecified,
    )
}