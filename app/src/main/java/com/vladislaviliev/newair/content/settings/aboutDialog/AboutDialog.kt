package com.vladislaviliev.newair.content.settings.aboutDialog

import android.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
internal fun AboutDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = { Icon(Icons.Default.Person, null) },
        text = {
            Text(
                "B. Wilsher\n" +
                        "I. Gylaris\n" +
                        "I. Watt\n" +
                        "L.P. Stannard\n" +
                        "Vladislav Iliev\n" +
                        "V.M.T. Godsell\n" +
                        "\nExternal resources:\n" +
                        "Freepik\n" +
                        "com.himanshoe.charty:2.0.0-alpha01"
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) { Text(stringResource(R.string.ok)) }
        }
    )
}