package com.vladislaviliev.newair.screens.home.screen.uiComponents

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
import androidx.compose.ui.res.stringResource
import com.vladislaviliev.newair.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onAddLocationClick: () -> Unit, onRefreshClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier, Arrangement.End) {
        IconButton(onAddLocationClick) {
            Icon(Icons.Default.Add, stringResource(R.string.add_new_location))
        }

        IconButton(onRefreshClick) {
            Icon(Icons.Default.Refresh, stringResource(R.string.refresh))
        }
    }
}