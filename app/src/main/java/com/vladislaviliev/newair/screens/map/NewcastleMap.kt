package com.vladislaviliev.newair.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
internal fun NewcastleMap(
    modifier: Modifier = Modifier,
    onMapClick: ((LatLng) -> Unit)? = null,
    content: @Composable @GoogleMapComposable () -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(54.979190, -1.614668), 14f)
    }

    GoogleMap(
        modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick,
        content = content
    )
}