package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.R

@Composable
fun Timestamp(timestamp: String, modifier: Modifier = Modifier) {
    val description = stringResource(R.string.timestamp_x, timestamp)
    Text(timestamp, modifier.semantics { contentDescription = description }, fontSize = 20.sp)
}