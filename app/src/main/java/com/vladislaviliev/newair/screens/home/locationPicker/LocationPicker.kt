package com.vladislaviliev.newair.screens.home.locationPicker

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.vladislaviliev.newair.user.location.paging.UserLocationPagerModel
import com.vladislaviliev.newair.user.location.paging.itemProviders.RadioButtonProvider

@Composable
fun LocationPicker(
    items: LazyPagingItems<UserLocationPagerModel>,
    idToPreselect: Int,
    onLocationSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(idToPreselect) }
    val onItemClicked = { id: Int -> selectedItem = id }
    val onConfirm = { onLocationSelected(selectedItem) }

    AlertDialog(
        modifier = modifier.heightIn(max = 400.dp),
        onDismissRequest = onDismiss,
        title = { Text("Select location") },
        text = { RadioButtonProvider()(items, setOf(selectedItem), onItemClicked) },
        confirmButton = { Button(onConfirm) { Text("Confirm") } },
        dismissButton = { Button(onDismiss) { Text("Cancel") } }
    )
}