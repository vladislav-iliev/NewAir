package com.vladislaviliev.newair.content.map.addLocation.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladislaviliev.newair.content.map.addLocation.AddLocationState

@Composable
internal fun AddLocationDialog(
    state: AddLocationState,
    onConfirm: (String) -> Unit,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissed,
        modifier = modifier,
        text = { InputField(state, name, { name = it }) },
        confirmButton = {
            TextButton({ onConfirm(name) }, enabled = name.isNotBlank() && !state.isAdding) {
                Text("Confirm")
            }
        },
        dismissButton = { TextButton(onDismissed) { Text("Cancel") } },
        icon = { Icon(Icons.Default.LocationOn, null) },
        title = { Text("Enter location name") }
    )
}

@Composable
private fun InputField(
    state: AddLocationState,
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        name,
        onNameChange,
        modifier,
        placeholder = { Text("New location name") },
        isError = state.errorMessage.isNotBlank(),
        label = {
            if (state.errorMessage.isNotBlank()) Text(state.errorMessage, modifier)
            else if (state.successMessage.isNotBlank()) Text(state.successMessage, modifier)
        }
    )
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun AddLocationPreview() {
    AddLocationDialog(AddLocationState(false, "", ""), {}, {})
}