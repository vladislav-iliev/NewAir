package com.vladislaviliev.newair.screens.graph.uiComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onRefreshClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        { Text("Past 7 days, PM10 μg/m") },
        modifier,
        actions = {
            IconButton(onRefreshClick) { Icon(Icons.Default.Refresh, "Refresh") }
        },
    )
}