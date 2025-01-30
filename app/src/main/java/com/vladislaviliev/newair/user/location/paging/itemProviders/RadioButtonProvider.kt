package com.vladislaviliev.newair.user.location.paging.itemProviders

import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class RadioButtonProvider : ItemsProvider() {

    @Composable
    override fun ProvideItem(isChecked: Boolean, modifier: Modifier) {
        RadioButton(isChecked, null, modifier)
    }
}