package com.vladislaviliev.newair.screens.map.addLocation.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vladislaviliev.newair.R

@Composable
internal fun Dialog(
    state: State,
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
            TextButton({ onConfirm(name) }, enabled = state !is State.Loading) { Text("Confirm") }
        },
        dismissButton = { TextButton(onDismissed) { Text("Cancel") } },
        icon = { Icon(Icons.Default.LocationOn, null) },
        title = { Text("Enter location name") }
    )
}

@Composable
private fun InputField(
    state: State, name: String, onNameChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(
        name,
        onNameChange,
        modifier,
        placeholder = { Text("New location name") },
        isError = state is State.Error,
        label = createLabel(state),
        trailingIcon = { if (state is State.Loading) CircularProgressIndicator() }
    )
}

private fun createLabel(state: State): @Composable (() -> Unit)? = when (state) {
    is State.Error -> ({ Text(stringResource(state.msg, state.name)) })
    is State.Success -> ({ Text(stringResource(R.string.added_x, state.name)) })
    else -> null
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun AddLocationPreview() {
    Dialog(State.Idle(), {}, {})
}