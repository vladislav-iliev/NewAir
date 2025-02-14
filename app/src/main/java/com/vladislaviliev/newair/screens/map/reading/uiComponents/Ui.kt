package com.vladislaviliev.newair.screens.map.reading.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.readings.calculations.Health

@Composable
fun Ui(
    onRefreshClick: () -> Unit,
    @StringRes message: Int,
    errorMessage: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.End) {

        Messages(message, errorMessage, timestamp)

        Button(onRefreshClick) {
            Icon(Icons.Default.Refresh, "Refresh")
        }

        var isShowingLegend by rememberSaveable { mutableStateOf(false) }
        Button({ isShowingLegend = !isShowingLegend }) {
            Icon(Icons.Default.Info, "Legend")
        }
        if (isShowingLegend) Legend()
    }
}

@Composable
fun Messages(
    @StringRes message: Int, errorMessage: String, timestamp: String, modifier: Modifier = Modifier
) {
    Surface(modifier) {
        val fontSize = 20.sp

        Column {
            if (message != R.string.empty_placeholder)
                Text(stringResource(message), fontSize = fontSize)
            if (errorMessage.isNotBlank())
                Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = fontSize)
            if (timestamp.isNotBlank())
                Text(timestamp, fontSize = fontSize)
        }
    }
}

@Composable
private fun Legend(modifier: Modifier = Modifier) {
    Surface(shape = MaterialTheme.shapes.large) {
        Column(modifier) {
            Spacer(Modifier.height(10.dp))

            val rowsData = Health.getThresholdsToColors()
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