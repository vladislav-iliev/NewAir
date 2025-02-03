package com.vladislaviliev.newair.screens.graph.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.screens.StateConstants

@Composable
fun Messages(@StringRes message: Int, errorMessage: String, timestamp: String) {
    val fontSize = 20.sp

    Column {
        if (message != StateConstants.emptyPlaceholder)
            Text(stringResource(message), fontSize = fontSize)

        if (errorMessage.isNotBlank())
            Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = fontSize)

        if (timestamp.isNotBlank())
            Text(timestamp, fontSize = fontSize)
    }
}