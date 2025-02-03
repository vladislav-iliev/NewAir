package com.vladislaviliev.newair.user.location.paging

import com.vladislaviliev.newair.user.location.UserLocation

object Transformers {

    fun toPagerModel(loc: UserLocation) = Model.Location(loc.id, loc.name)

    fun insertSeparators(before: Model?, after: Model?): Model.Header? {

        if (isFirstItem(before, after)
            || isBetweenTwoLocationsWithDifferentLetters(before, after)
        ) {
            val after = after as Model.Location
            return Model.Header(after.title.first().uppercaseChar())
        }
        return null
    }

    private fun isFirstItem(before: Model?, after: Model?) =
        before == null && after is Model.Location

    private fun isBetweenTwoLocationsWithDifferentLetters(before: Model?, after: Model?) =
        before is Model.Location
                && after is Model.Location
                && before.title.first().uppercaseChar() != after.title.first().uppercaseChar()
}