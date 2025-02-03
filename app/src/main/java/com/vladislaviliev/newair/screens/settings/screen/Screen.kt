package com.vladislaviliev.newair.screens.settings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun Screen(
    isColorBlind: Boolean,
    onColorBlindToggle: (Boolean) -> Unit,
    onDeleteLocationClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(modifier.fillMaxSize()) {

        Column(Modifier.statusBarsPadding()) {

            ListItem(
                leadingContent = { Icon(Icons.Default.Info, null) },
                headlineContent = { Text("Colourblind mode") },
                trailingContent = { Switch(isColorBlind, null) },
                modifier = Modifier
                    .toggleable(value = isColorBlind, onValueChange = onColorBlindToggle)
            )
            HorizontalDivider()

            ListItem(
                leadingContent = { Icon(Icons.Default.Delete, null) },
                headlineContent = { Text("Delete location") },
                modifier = Modifier.clickable(onClick = onDeleteLocationClick)
            )
            HorizontalDivider()

            ListItem(
                leadingContent = { Icon(Icons.Default.Delete, null) },
                headlineContent = { Text("Delete all locations") },
                modifier = Modifier.clickable(onClick = onDeleteAllClick)
            )
            HorizontalDivider()

            ListItem(
                leadingContent = { Icon(Icons.Default.Info, null) },
                headlineContent = { Text("Info") },
                modifier = Modifier.clickable(onClick = onAboutClick)
            )
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewSettings() {
    Screen(false, {}, {}, {}, {}, Modifier.fillMaxSize())
}