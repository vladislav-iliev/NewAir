package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Messages(@StringRes message: Int, errorMessage: String, modifier: Modifier = Modifier) {
    Text(
        stringResource(message) + if (errorMessage.isNotBlank()) "\n$errorMessage" else "",
        modifier.wrapContentSize(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
    )
}