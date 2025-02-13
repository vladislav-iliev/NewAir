package com.vladislaviliev.newair.user.location.paging.itemProviders

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.vladislaviliev.newair.user.location.paging.Model
import com.vladislaviliev.newair.user.location.paging.contentTypeFactory
import com.vladislaviliev.newair.user.location.paging.keyFactory

abstract class ItemProvider {

    @Composable
    abstract fun Indicator(isChecked: Boolean, modifier: Modifier = Modifier)

    abstract fun Modifier.columnModifier(): Modifier

    abstract fun Modifier.itemModifier(selected: Boolean, onClick: () -> Unit): Modifier

    @Composable
    operator fun invoke(
        items: LazyPagingItems<Model>,
        selectedIds: Collection<Int>,
        onItemClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier.columnModifier()) {

            if (items.loadState.prepend is LoadState.Loading) {
                item { CircularProgressIndicator() }
            }

            paginatedItems(this, items, selectedIds, onItemClicked)

            if (items.loadState.append is LoadState.Loading) {
                item { CircularProgressIndicator() }
            }
        }
    }

    private fun paginatedItems(
        scope: LazyListScope,
        items: LazyPagingItems<Model>,
        selectedIds: Collection<Int>,
        onItemClicked: (Int) -> Unit,
    ) {
        scope.items(
            count = items.itemCount,
            key = { keyFactory(items.peek(it)!!) },
            contentType = { contentTypeFactory(items.peek(it)!!) }
        ) { index ->

            val item = items[index]!!
            val widthModifier = Modifier.fillMaxWidth()

            if (item is Model.Location)
                ItemRow(item.id in selectedIds, onItemClicked, item, widthModifier)
            else
                Text((item as Model.Header).char.toString(), widthModifier)
        }
    }

    @Composable
    private fun ItemRow(
        isChecked: Boolean,
        onClick: (Int) -> Unit,
        item: Model.Location,
        modifier: Modifier = Modifier
    ) {
        ListItem(
            { Text(item.title) },
            modifier.itemModifier(isChecked) { onClick(item.id) },
            leadingContent = { Indicator(isChecked) },
            colors = ListItemDefaults.colors(Color.Transparent)
        )
    }
}