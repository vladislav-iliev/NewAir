package com.vladislaviliev.newair.screens.map.reading.uiComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.live.LiveReading
import com.vladislaviliev.newair.screens.map.NewcastleMap
import kotlin.collections.forEach

@Composable
fun Map(readings: Iterable<LiveReading>, modifier: Modifier = Modifier) {
    NewcastleMap(modifier) {
        readings.forEach { reading ->
            Circle(
                LatLng(reading.latitude, reading.longitude),
                radius = 100.0,
                strokeWidth = 0f,
                fillColor = Health.getColor(reading.measure).copy(alpha = 0.5f)
            )
        }
    }
}