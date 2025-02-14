package com.vladislaviliev.newair.screens.paging

fun keyFactory(item: Model) = when (item) {
    is Model.Location -> item.id
    else -> (item as Model.Header).char
}