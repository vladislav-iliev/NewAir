package com.vladislaviliev.newair.screens.graph.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladislaviliev.air.readings.history.HistoryReading
import com.vladislaviliev.newair.screens.graph.state.Loading
import com.vladislaviliev.newair.screens.graph.state.State

@Composable
internal fun Screen(
    onRefreshClick: () -> Unit, state: State, modifier: Modifier = Modifier
) {
    Screen(
        onRefreshClick, state.message, state.errorMessage, state.timestamp, state.readings, modifier
    )
}

@Composable
private fun Screen(
    onRefreshClick: () -> Unit,
    @StringRes message: Int,
    errorMessage: String,
    timestamp: String,
    readings: Collection<HistoryReading>,
    modifier: Modifier = Modifier
) {
    Surface(modifier.fillMaxSize()) {
        Column {
            TopAppBar(onRefreshClick)
            Content(message, errorMessage, timestamp, readings)
        }
    }
}

@Composable
private fun Content(
    @StringRes message: Int,
    errorMessage: String,
    timestamp: String,
    readings: Collection<HistoryReading>,
) {
    Messages(message, errorMessage, timestamp)
    if (readings.isEmpty()) return
    Graph(readings)
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewGraph() {
    Screen({}, Loading.value, Modifier.fillMaxSize())
}