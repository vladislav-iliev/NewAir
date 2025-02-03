package com.vladislaviliev.newair.content.home.mainScreen.state

import androidx.compose.ui.graphics.Color
import com.vladislaviliev.newair.content.ScreenStateConstants

data object HomeScreenStateLoading {
    val value = HomeScreenState(
        Color.Unspecified,
        Color.Unspecified,
        ScreenStateConstants.LOADING,
        ScreenStateConstants.UNKNOWN,
        ScreenStateConstants.LOADING,
        ScreenStateConstants.UNKNOWN,
        ScreenStateConstants.UNKNOWN,
        ScreenStateConstants.LOADING,
    )
}