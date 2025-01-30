package com.vladislaviliev.newair.content.home.mainScreen.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onAddLocationClick: () -> Unit, onRefreshClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier, Arrangement.End) {
        IconButton(onAddLocationClick) {
            Icon(Icons.Default.Add, "Add new location")
        }

        IconButton(onRefreshClick) {
            Icon(Icons.Default.Refresh, "Refresh")
        }
    }
}