package com.vladislaviliev.newair.screens.home.screen.state

import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.screens.StateConstants

data object Loading {
    val value = State(
        "", StateConstants.loading, "", "", "", "", "", Color.Unspecified, Color.Unspecified,
    )
}