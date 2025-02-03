package com.vladislaviliev.newair.user.location.paging

fun contentTypeFactory(item: Model) = when (item) {
    is Model.Location -> 0
    is Model.Header -> 1
    else -> 2
}