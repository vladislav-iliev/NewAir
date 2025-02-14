package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.R

@Composable
fun Messages(@StringRes messageRes: Int, errorMessage: String, modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.Center) {

        val messageColor =
            if (messageRes == R.string.error) MaterialTheme.colorScheme.error
            else Color.Unspecified
        val message = stringResource(messageRes)
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

        if (messageRes == R.string.loading) CircularProgressIndicator(
            Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally)
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