package com.vladislaviliev.newair.map.addLocation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.vladislaviliev.newair.map.NewcastleMap

@Composable
fun AddLocationScreen(
    onAddLocationClick: (Double, Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMarkerPlaced by rememberSaveable { mutableStateOf(false) }
    val markerState = rememberMarkerState()

    val onMapClick: (LatLng) -> Unit = {
        isMarkerPlaced = true
        markerState.position = it
    }

    NewcastleMap(modifier, onMapClick) {

        if (isMarkerPlaced) {
            Marker(markerState, "New location position")
        }
    }

    val markerLat = markerState.position.latitude
    val markerLng = markerState.position.longitude
    Ui(isMarkerPlaced, { onAddLocationClick(markerLat, markerLng) })
}

@Composable
private fun Ui(
    isMarkerPlaced: Boolean,
    onAddLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Surface(Modifier.align(Alignment.TopCenter)) { Text("Add location", fontSize = 20.sp) }

        FilledIconButton(
            onAddLocationClick, Modifier.align(Alignment.BottomCenter), isMarkerPlaced
        ) {
            Icon(Icons.Default.Add, "Add location")
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewAddLocationMap() {
    Ui(true, {}, Modifier.fillMaxWidth())
}