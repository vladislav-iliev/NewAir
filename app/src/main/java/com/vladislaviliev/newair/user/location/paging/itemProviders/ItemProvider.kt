package com.vladislaviliev.newair.user.location.paging.itemProviders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.vladislaviliev.newair.user.location.paging.Model
import com.vladislaviliev.newair.user.location.paging.contentTypeFactory
import com.vladislaviliev.newair.user.location.paging.keyFactory

abstract class ItemProvider {

    @Composable
    operator fun invoke(
        items: LazyPagingItems<Model>,
        selectedIds: Collection<Int>,
        onItemClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier) {

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
        Row(
            modifier.clickable { onClick(item.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideItem(isChecked)
            Text(item.title)
        }
    }

    @Composable
    abstract fun ProvideItem(isChecked: Boolean, modifier: Modifier = Modifier)

}