package com.vladislaviliev.newair.content.graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphTopAppBar(onRefreshClick: () -> Unit, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        { Text("Past 7 days, PM10 μg/m") },
        modifier,
        actions = {
            IconButton(onRefreshClick) { Icon(Icons.Default.Refresh, "Refresh") }
        },
    )
}