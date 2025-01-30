package com.vladislaviliev.newair.user.location.paging

import com.vladislaviliev.newair.user.location.UserLocation

object UserLocationPagerTransformers {

    fun toPagerModel(loc: UserLocation) = UserLocationPagerModel.Location(loc.id, loc.name)

    fun insertSeparators(
        before: UserLocationPagerModel?,
        after: UserLocationPagerModel?
    ): UserLocationPagerModel.Header? {

        if (isFirstItem(before, after)
            || isBetweenTwoLocationsWithDifferentLetters(before, after)
        ) {
            val after = after as UserLocationPagerModel.Location
            return UserLocationPagerModel.Header(after.title.first().uppercaseChar())
        }
        return null
    }

    private fun isFirstItem(before: UserLocationPagerModel?, after: UserLocationPagerModel?) =
        before == null && after is UserLocationPagerModel.Location

    private fun isBetweenTwoLocationsWithDifferentLetters(
        before: UserLocationPagerModel?,
        after: UserLocationPagerModel?
    ) = before is UserLocationPagerModel.Location
            && after is UserLocationPagerModel.Location
            && before.title.first().uppercaseChar() != after.title.first().uppercaseChar()
}