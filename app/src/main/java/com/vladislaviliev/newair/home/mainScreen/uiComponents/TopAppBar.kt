package com.vladislaviliev.newair.home.mainScreen.uiComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onAddLocationClick: () -> Unit,
    onRefreshClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        {},
        modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            actionIconContentColor = contentColor
        ),
        actions = {
            IconButton(onAddLocationClick) {
                Icon(Icons.Default.Add, "Add new location")
            }

            IconButton(onRefreshClick) {
                Icon(Icons.Default.Refresh, "Refresh")
            }
        },
    )
}