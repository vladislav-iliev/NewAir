package com.vladislaviliev.newair.screens.paging

fun contentTypeFactory(item: Model) = when (item) {
    is Model.Location -> 0
    is Model.Header -> 1
    else -> 2
}