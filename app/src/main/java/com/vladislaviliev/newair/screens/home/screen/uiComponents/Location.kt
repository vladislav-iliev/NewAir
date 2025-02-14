package com.vladislaviliev.newair.screens.home.screen.uiComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vladislaviliev.newair.R

/** Careful when changing, this might already be stored in a database **/
const val cityNamePlaceholder = "_city_"

@Composable
fun Location(name: String, onLocationPickerClick: () -> Unit, modifier: Modifier = Modifier) {

    val colors = ButtonDefaults.textButtonColors().copy(contentColor = LocalContentColor.current)

    val nameLocalized = if (name == cityNamePlaceholder) stringResource(R.string.city) else name
    val textDescription = stringResource(R.string.location_x, nameLocalized)

    TextButton(onLocationPickerClick, modifier, colors = colors) {
        Text(
            nameLocalized,
            Modifier.semantics { contentDescription = textDescription },
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Icon(Icons.Default.ArrowDropDown, stringResource(R.string.click_to_change_location))
    }
}