package com.vladislaviliev.newair.user.location.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.insertSeparators
import androidx.paging.map
import com.vladislaviliev.newair.user.location.UserLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Transformer {

    fun flowOf(p: PagingSource<Int, UserLocation>): Flow<PagingData<Model>> {

        val pageSize = 20
        val prefetchDistance = 10
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize * 2,
            prefetchDistance = prefetchDistance,
            maxSize = 2 * prefetchDistance + pageSize,
            enablePlaceholders = false,
        )

        return Pager(config) { p }.flow.map {
            it.map(Transformer::toPagerModel)
                .insertSeparators(generator = Transformer::insertSeparators)
        }
    }

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
}