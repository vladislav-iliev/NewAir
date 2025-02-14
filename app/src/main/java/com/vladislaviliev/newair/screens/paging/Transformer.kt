package com.vladislaviliev.newair.screens.paging

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.vladislaviliev.newair.user.location.UserLocation

class Transformer {

    private fun toPagerModel(loc: UserLocation) = Model.Location(loc.id, loc.name)

    private fun insertSeparators(before: Model?, after: Model?): Model.Header? {
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

    fun transform(data: PagingData<UserLocation>) =
        data.map(::toPagerModel).insertSeparators(generator = ::insertSeparators)
}