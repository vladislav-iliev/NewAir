package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.screens.home.screen.state.State
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.readings.calculations.Health

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

            Text(
                location,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )

            Circle(
                pollution,
                Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )
            IconButton(onLocationPickerClick, Modifier.align(Alignment.CenterHorizontally)) {
                Icon(Icons.Default.ArrowDropDown, "Select location")
            }

            Messages(
                message, errorMessage,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f)
                    .weight(5f),
            )

            Indicators(temperature, humidity, Modifier.fillMaxWidth())

            Spacer(Modifier.weight(3f))

            Text(
                timestamp,
                Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
            )
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewHome() {
    Screen({}, {}, {}, Loading.value, Modifier.fillMaxSize())
}