package com.vladislaviliev.newair.screens.settings.aboutDialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vladislaviliev.newair.R

@Composable
internal fun AboutDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = { Icon(Icons.Default.Person, null) },
        text = { Text(stringResource(R.string.about_dialog_contents)) },
        confirmButton = {
            TextButton(onClick = onDismissRequest) { Text(stringResource(android.R.string.ok)) }
        }
    )
}