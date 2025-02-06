package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladislaviliev.newair.readings.calculations.Health
import com.vladislaviliev.newair.readings.downloader.metadata.Metadata
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.screens.home.screen.state.State
import com.vladislaviliev.newair.screens.home.screen.state.Transformer
import com.vladislaviliev.newair.ui.theme.NewAirTheme
import com.vladislaviliev.newair.user.location.DefaultUserLocation

@Composable
internal fun Screen(
    onAddLocationClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onLocationPickerClick: () -> Unit,
    state: State,
    modifier: Modifier = Modifier,
) {
    Screen(
        onAddLocationClick,
        onRefreshClick,
        onLocationPickerClick,
        state.location,
        state.message,
        state.errorMessage,
        state.timestamp,
        state.pollution,
        state.temperature,
        state.humidity,
        state.backgroundColor,
        state.contentColor,
        modifier,
    )
}

@Composable
private fun Screen(
    onAddLocationClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onLocationPickerClick: () -> Unit,
    location: String,
    @StringRes message: Int,
    errorMessage: String,
    timestamp: String,
    pollution: String,
    temperature: String,
    humidity: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier.fillMaxSize(),
        color = Health.checkUnspecifiedBackgroundColor(backgroundColor),
        contentColor = Health.checkUnspecifiedContentColor(contentColor)
    ) {
        BackgroundImages(Modifier.fillMaxSize())

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {

            TopAppBar(onAddLocationClick, onRefreshClick, Modifier.fillMaxWidth())

            if (location.isNotBlank()) Location(
                location, onLocationPickerClick, Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (pollution.isNotBlank()) Circle(
                pollution, Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Messages(
                message, errorMessage,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f)
                    .weight(5f),
            )

            if (temperature.isNotBlank() && humidity.isNotBlank())
                TempHumidIndicators(temperature, humidity, Modifier.fillMaxWidth())

            Spacer(Modifier.weight(3f))

            if (timestamp.isNotBlank())
                Timestamp(timestamp, Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewHome() {
    NewAirTheme {
        val metadata = Metadata("", "now")
        val state = Transformer.stateOf(
            DefaultUserLocation.value,
            LiveResponse(false, emptyList(), metadata)
        )
        Screen({}, {}, {}, state, Modifier.fillMaxSize())
    }
}