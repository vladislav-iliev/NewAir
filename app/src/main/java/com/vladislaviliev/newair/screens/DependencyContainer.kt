package com.vladislaviliev.newair.screens

import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DependencyContainer {

    @Provides
    fun providePagingConfig(): PagingConfig {
        val pageSize = 20
        val prefetchDistance = 10
        return PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize * 2,
            prefetchDistance = prefetchDistance,
            maxSize = 2 * prefetchDistance + pageSize,
            enablePlaceholders = false,
        )
    }
}