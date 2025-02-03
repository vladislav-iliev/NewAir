package com.vladislaviliev.newair.content.home.mainScreen.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
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
import com.vladislaviliev.newair.content.home.mainScreen.state.HomeScreenState
import com.vladislaviliev.newair.content.home.mainScreen.state.HomeScreenStateLoading
import com.vladislaviliev.newair.readings.calculations.Health

@Composable
internal fun HomeScreen(
    state: HomeScreenState,
    onAddLocationClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onLocationPickerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScreen(
        onAddLocationClick,
        onRefreshClick,
        onLocationPickerClick,
        state.backgroundColor,
        state.contentColor,
        state.location,
        state.pollution,
        state.message,
        state.temperature,
        state.humidity,
        state.timestamp,
        modifier,
    )
}

@Composable
private fun HomeScreen(
    onAddLocationClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onLocationPickerClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    location: String,
    pollution: String,
    healthMessage: String,
    temperature: String,
    humidity: String,
    timestamp: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier.fillMaxSize(),
        color = Health.checkUnspecifiedBackgroundColor(backgroundColor),
        contentColor = Health.checkUnspecifiedContentColor(contentColor)
    ) {

        BackgroundImages(Modifier.fillMaxSize())

        Column(Modifier
            .fillMaxWidth()
            .systemBarsPadding()) {

            HomeTopAppBar(onAddLocationClick, onRefreshClick, Modifier.fillMaxWidth())

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

            Text(
                healthMessage,
                Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
                    .weight(5f),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
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
    HomeScreen(HomeScreenStateLoading.value, {}, {}, {}, Modifier.fillMaxSize())
}