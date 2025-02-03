package com.vladislaviliev.newair.screens.map.reading.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladislaviliev.newair.screens.StateConstants
import com.vladislaviliev.newair.screens.map.reading.state.State
import com.vladislaviliev.newair.readings.live.LiveReading

@Composable
fun Screen(onRefreshClick: () -> Unit, state: State, modifier: Modifier = Modifier) {
    Screen(
        onRefreshClick,
        state.message,
        state.errorMessage,
        state.timestamp,
        state.isColorBlind,
        state.readings,
        modifier
    )
}

@Composable
private fun Screen(
    onRefreshClick: () -> Unit,
    @StringRes message: Int,
    errorMessage: String,
    timestamp: String,
    isColorBlind: Boolean,
    readings: Iterable<LiveReading>,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        Map(isColorBlind, readings, modifier)
        Ui(
            onRefreshClick,
            message,
            errorMessage,
            timestamp,
            isColorBlind,
            Modifier
                .align(Alignment.TopEnd)
                .systemBarsPadding()
        )
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewCircleMap() {
    Ui({}, StateConstants.loading, "Error", "time", true, Modifier.fillMaxWidth())
}