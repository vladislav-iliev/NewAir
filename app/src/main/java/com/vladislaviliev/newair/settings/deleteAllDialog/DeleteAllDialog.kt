package com.vladislaviliev.newair.settings.deleteAllDialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun DeleteAllDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onConfirm) { Text("Confirm") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } },
        icon = { Icon(Icons.Default.Delete, null) },
        title = { Text("Delete all user locations?") }
    )
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun DeleteAllPreview() {
    DeleteAllDialog({}, {})
}