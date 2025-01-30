package com.vladislaviliev.newair.user.location.paging

fun keyFactory(item: UserLocationPagerModel) = when (item) {
    is UserLocationPagerModel.Location -> item.id
    else -> (item as UserLocationPagerModel.Header).char
}