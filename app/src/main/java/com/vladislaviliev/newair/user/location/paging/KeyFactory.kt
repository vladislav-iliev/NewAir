package com.vladislaviliev.newair.user.location.paging

class KeyFactory {
    operator fun invoke(item: UserLocationPagerModel) = when (item) {
        is UserLocationPagerModel.Location -> item.id
        else -> (item as UserLocationPagerModel.Header).char
    }
}