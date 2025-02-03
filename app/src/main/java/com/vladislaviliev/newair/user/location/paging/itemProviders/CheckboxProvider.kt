package com.vladislaviliev.newair.user.location.paging.itemProviders

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class CheckboxProvider : ItemProvider() {

    @Composable
    override fun ProvideItem(isChecked: Boolean, modifier: Modifier) {
        Checkbox(isChecked, null, modifier)
    }
}