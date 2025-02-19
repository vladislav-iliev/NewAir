package com.vladislaviliev.newair.screens.paging.itemProviders

import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

class CheckboxProvider : ItemProvider() {

    @Composable
    override fun Indicator(isChecked: Boolean, modifier: Modifier) {
        Checkbox(isChecked, null, modifier)
    }

    override fun Modifier.columnModifier() = selectableGroup()

    override fun Modifier.itemModifier(selected: Boolean, onClick: () -> Unit) =
        toggleable(selected, role = Role.Checkbox) { onClick() }
}