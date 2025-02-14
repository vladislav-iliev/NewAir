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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.screens.paging.Model
import com.vladislaviliev.newair.screens.paging.itemProviders.RadioButtonProvider
import kotlinx.coroutines.flow.Flow

@Composable
fun LocationPicker(
    items: Flow<PagingData<Model>>,
    idToPreselect: Int,
    onLocationSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyItems = items.collectAsLazyPagingItems()
    LocationPicker(lazyItems, idToPreselect, onLocationSelected, onDismiss, modifier)
}

@Composable
private fun LocationPicker(
    items: LazyPagingItems<Model>,
    idToPreselect: Int,
    onLocationSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(idToPreselect) }
    val onItemClicked = { id: Int -> selectedItem = id }
    val onConfirm = { onLocationSelected(selectedItem) }

    AlertDialog(
        modifier = modifier.heightIn(max = dimensionResource(R.dimen.picker_dialog_max_height)),
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.select_location)) },
        text = { RadioButtonProvider().Provide(items, setOf(selectedItem), onItemClicked) },
        confirmButton = { Button(onConfirm) { Text(stringResource(android.R.string.ok)) } },
        dismissButton = { Button(onDismiss) { Text(stringResource(android.R.string.cancel)) } }
    )
}