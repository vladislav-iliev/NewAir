package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.screens.StateConstants

@Composable
fun Messages(@StringRes message: Int, errorMessage: String, modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.Center) {

        val messageColor =
            if (message == StateConstants.error) MaterialTheme.colorScheme.error
            else Color.Unspecified
        val message = stringResource(message)
        val messageDescription = stringResource(R.string.message_x, message)
        val errorMessageDescription = stringResource(R.string.error_message_x, errorMessage)
        val fontSize = 20.sp

        Text(
            message,
            Modifier
                .fillMaxWidth()
                .semantics { contentDescription = messageDescription },
            messageColor,
            fontSize,
            textAlign = TextAlign.Center
        )

        if (errorMessage.isNotBlank()) Text(
            errorMessage,
            Modifier
                .fillMaxWidth()
                .semantics { contentDescription = errorMessageDescription },
            fontSize = fontSize,
            textAlign = TextAlign.Center,
        )

    }
}