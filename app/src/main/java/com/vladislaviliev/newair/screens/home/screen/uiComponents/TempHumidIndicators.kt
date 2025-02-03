package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
internal fun Indicators(temp: String, humidity: String, modifier: Modifier = Modifier) {

    ConstraintLayout(modifier) {

        val (temperatureIndicator, humidityIndicator) = createRefs()
        val guidelineTemp = createGuidelineFromStart(0.2f)
        val guidelineHumidity = createGuidelineFromStart(0.6f)

        Indicator(
            Icons.Default.Thermostat,
            "Temperature",
            temp,
            Modifier.constrainAs(temperatureIndicator) {
                start.linkTo(guidelineTemp)
            })

        Indicator(
            Icons.Default.WaterDrop,
            "Humidity",
            humidity,
            Modifier.constrainAs(humidityIndicator) {
                start.linkTo(guidelineHumidity)
            })
    }
}

@Composable
private fun Indicator(
    icon: ImageVector,
    contentDescription: String,
    measurement: String,
    modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription)
        Text(measurement, fontSize = 16.sp)
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewIndicators() {
    Indicators(
        "4.0", "20.0",
        Modifier
            .fillMaxWidth()
            .background(Color.Red)
    )
}