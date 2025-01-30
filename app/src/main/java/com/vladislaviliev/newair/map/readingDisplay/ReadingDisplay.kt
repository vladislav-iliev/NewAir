package com.vladislaviliev.newair.map.readingDisplay

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.vladislaviliev.newair.map.NewcastleMap
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.live.LiveReading
import kotlin.collections.forEach

@Composable
fun ReadingDisplay(
    isColorBlind: Boolean,
    readings: Iterable<LiveReading>,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    NewcastleMap(modifier) {
        readings.forEach { reading ->
            Circle(
                LatLng(reading.latitude, reading.longitude),
                radius = 100.0,
                strokeWidth = 0f,
                fillColor = Health
                    .getColor(isColorBlind, reading.measure).copy(alpha = 0.5f)
            )
        }
    }
    Ui(isColorBlind, onRefreshClick)
}

@Composable
private fun Ui(isColorBlind: Boolean, onRefreshClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .statusBarsPadding(),
        Alignment.TopEnd
    ) {
        Surface {
            Column(horizontalAlignment = Alignment.End) {

                var showLegend by rememberSaveable { mutableStateOf(false) }

                val onInfoClick = { showLegend = !showLegend }

                Buttons(onRefreshClick, onInfoClick)

                if (showLegend) Legend(isColorBlind)
            }
        }
    }
}

@Composable
private fun Buttons(
    onRefreshClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        IconButton(onRefreshClick) {
            Icon(Icons.Default.Refresh, "Refresh")
        }
        IconButton(onInfoClick) {
            Icon(Icons.Default.Info, "Info")
        }
    }
}

@Composable
private fun Legend(isColorBlind: Boolean, modifier: Modifier = Modifier) {
    Column(modifier) {
        Spacer(Modifier.height(10.dp))

        val rowsData = Health.getThresholdsToColors(isColorBlind)
        rowsData.forEach { LegendRow(it) }

        Spacer(Modifier.height(2.dp))

        Text("PM10 μg/m", Modifier.padding(horizontal = 10.dp))

        Spacer(Modifier.height(10.dp))
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
    Ui(true, {}, Modifier.fillMaxWidth())
}