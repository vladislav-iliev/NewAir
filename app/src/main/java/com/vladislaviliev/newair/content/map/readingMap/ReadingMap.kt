package com.vladislaviliev.newair.content.map.readingMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.vladislaviliev.newair.content.map.NewcastleMap
import com.vladislaviliev.newair.content.map.readingMap.state.ReadingMapState
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.live.LiveReading
import kotlin.collections.forEach

@Composable
fun ReadingDisplay(
    state: ReadingMapState, onRefreshClick: () -> Unit, modifier: Modifier = Modifier,
) {
    ReadingDisplay(state.isColorBlind, state.readings, state.message, onRefreshClick, modifier)
}

@Composable
private fun ReadingDisplay(
    isColorBlind: Boolean,
    readings: Iterable<LiveReading>,
    message: String,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        Map(isColorBlind, readings, modifier)
        Ui(
            isColorBlind,
            message,
            onRefreshClick,
            Modifier
                .align(Alignment.TopEnd)
                .systemBarsPadding()
        )
    }
}

@Composable
private fun Map(
    isColorBlind: Boolean,
    readings: Iterable<LiveReading>,
    modifier: Modifier = Modifier
) {
    NewcastleMap(modifier) {
        readings.forEach { reading ->
            Circle(
                LatLng(reading.latitude, reading.longitude),
                radius = 100.0,
                strokeWidth = 0f,
                fillColor = Health.getColor(isColorBlind, reading.measure).copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun Ui(
    isColorBlind: Boolean,
    message: String,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.End) {

        Surface { Text(message, fontSize = 20.sp) }

        Button(onRefreshClick) {
            Icon(Icons.Default.Refresh, "Refresh")
        }

        var isShowingLegend by rememberSaveable { mutableStateOf(false) }
        Button({ isShowingLegend = !isShowingLegend }) {
            Icon(Icons.Default.Info, "Legend")
        }
        if (isShowingLegend) Legend(isColorBlind)
    }
}

@Composable
private fun Legend(isColorBlind: Boolean, modifier: Modifier = Modifier) {
    Surface(shape = MaterialTheme.shapes.large) {
        Column(modifier) {
            Spacer(Modifier.height(10.dp))

            val rowsData = Health.getThresholdsToColors(isColorBlind)
            rowsData.forEach { LegendRow(it) }

            Spacer(Modifier.height(2.dp))

            Text("PM10 μg/m", Modifier.padding(horizontal = 10.dp))

            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun LegendRow(data: Pair<String, Color>, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Spacer(Modifier.width(10.dp))
        Box(
            Modifier
                .background(data.second)
                .size(10.dp)
        )
        Text("\t ${data.first}")
        Spacer(Modifier.width(10.dp))
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewCircleMap() {
    Ui(true, "No data", {}, Modifier.fillMaxWidth())
}