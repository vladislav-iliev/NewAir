package com.vladislaviliev.newair.content.home.mainScreen.state

import androidx.compose.ui.graphics.Color

data object HomeScreenStateUnspecified {
    val value = HomeScreenState(
        Color.Unspecified,
        Color.Unspecified,
        HomeScreenStateConstants.LOADING,
        HomeScreenStateConstants.UNKNOWN,
        HomeScreenStateConstants.LOADING,
        HomeScreenStateConstants.UNKNOWN,
        HomeScreenStateConstants.UNKNOWN,
        HomeScreenStateConstants.LOADING,
    )
}