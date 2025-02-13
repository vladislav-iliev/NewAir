package com.vladislaviliev.newair.user.location.paging.itemProviders

import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

class RadioButtonProvider : ItemProvider() {

    @Composable
    override fun Indicator(isChecked: Boolean, modifier: Modifier) {
        RadioButton(isChecked, null, modifier)
    }

    override fun Modifier.columnModifier() = this

    override fun Modifier.itemModifier(selected: Boolean, onClick: () -> Unit) =
        this then selectable(selected, role = Role.RadioButton, onClick = onClick)
}