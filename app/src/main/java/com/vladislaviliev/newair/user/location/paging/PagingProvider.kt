package com.vladislaviliev.newair.user.location.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.insertSeparators
import androidx.paging.map
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationDao
import kotlinx.coroutines.flow.map

class PagingProvider(private val dao: UserLocationDao, private val config: PagingConfig) {

    private fun newPagingSource() = dao.newPagingSource(emptyList())
    private fun newPagingSourceDelete() = dao.newPagingSource(listOf(DefaultUserLocation.value.id))

    private fun newPagingFlow(pagingSourceFactory: () -> PagingSource<Int, UserLocation>) =
        Pager(config, pagingSourceFactory = pagingSourceFactory)
            .flow
            .map {
                it.map(Transformers::toPagerModel)
                    .insertSeparators(generator = Transformers::insertSeparators)
            }

    fun newPagingFlowSelect() = newPagingFlow(::newPagingSource)
    fun newPagingFlowDelete() = newPagingFlow(::newPagingSourceDelete)

}