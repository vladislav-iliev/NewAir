package com.vladislaviliev.newair.screens.settings.deleteFromListDialog

import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.vladislaviliev.newair.user.location.paging.UserLocationPagerModel
import com.vladislaviliev.newair.user.location.paging.itemProviders.CheckboxProvider

@Composable
internal fun Dialog(
    items: LazyPagingItems<UserLocationPagerModel>,
    onLocationsSelected: (Collection<Int>) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedItems = remember { mutableStateListOf<Int>() }

    val onItemClicked: (Int) -> Unit = {
        if (selectedItems.contains(it)) selectedItems.remove(it)
        else selectedItems.add(it)
    }
    val onConfirm = { onLocationsSelected(selectedItems) }

    AlertDialog(
        modifier = modifier.height(400.dp),
        onDismissRequest = onDismiss,
        title = { Text("Select location") },
        text = { CheckboxProvider()(items, selectedItems.toSet(), onItemClicked) },
        confirmButton = { Button(onConfirm) { Text("Confirm") } },
        dismissButton = { Button(onDismiss) { Text("Cancel") } }
    )
}