package com.vladislaviliev.newair.user.location.paging

class ContentTypeFactory {
    operator fun invoke(item: UserLocationPagerModel) = when (item) {
        is UserLocationPagerModel.Location -> 0
        is UserLocationPagerModel.Header -> 1
        else -> 2
    }
}