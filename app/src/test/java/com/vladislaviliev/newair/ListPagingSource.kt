package com.vladislaviliev.newair

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vladislaviliev.newair.user.location.UserLocation

class ListPagingSource(private val data: List<UserLocation>) : PagingSource<Int, UserLocation>() {

    override fun getRefreshKey(state: PagingState<Int, UserLocation>): Int {
        val anchor = state.anchorPosition ?: return 0
        val pageSize = state.config.pageSize
        val initialLoadSize = state.config.initialLoadSize

        val itemPosToCenterAnchorInNextLoad = (anchor - initialLoadSize / 2).coerceAtLeast(0)
        val getStartOfPage = { i: Int -> i / pageSize * pageSize }
        return getStartOfPage(itemPosToCenterAnchorInNextLoad)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserLocation> {
        val key = params.key ?: 0
        val startIdx = key
        val endIdx = startIdx + params.loadSize - 1
        val response = data.slice(startIdx..endIdx)

        val prevKey = if (key == 0) null else key - params.loadSize

        val nextPos = endIdx + 1
        val reachedEnd = response.size < params.loadSize || data.size <= nextPos
        val nextKey = if (reachedEnd) null else nextPos

        return LoadResult.Page(response, prevKey, nextKey)
    }
}