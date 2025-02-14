package com.vladislaviliev.newair.screens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.PagingConfig
import com.vladislaviliev.newair.dependencies.PreferencesDependency
import com.vladislaviliev.newair.dependencies.ReadingsDependency
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.UserDatabase
import com.vladislaviliev.newair.user.location.UserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.preferences.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.vladislaviliev.newair.readings.downloader.metadata.PersistentDao as MetadataDao
import com.vladislaviliev.newair.user.preferences.PersistentDao as PreferencesDao

@Module
@InstallIn(ViewModelComponent::class)
class DependencyContainer {

    @Provides
    fun provideCityId(city: UserLocation) = city.id

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

    @Provides
    fun provideLocationsRepository(
        scope: CoroutineScope,
        db: UserDatabase,
        cityId: Int,
    ) = UserLocationsRepository(scope, Dispatchers.IO, db.userLocationDao(), cityId)

    @Provides
    fun provideReadingsRepository(
        scope: CoroutineScope,
        db: ReadingsDatabase,
        @ReadingsDependency metadataDataStore: DataStore<Preferences>,
    ) = ResponseRepository(
        scope,
        Dispatchers.IO,
        Downloader(),
        db.liveDao(),
        db.historyDao(),
        MetadataDao(metadataDataStore),
    )

    @Provides
    fun providesSettingsRepository(
        scope: CoroutineScope,
        @PreferencesDependency settingsDataStore: DataStore<Preferences>,
        defaultLocationId: Int,
    ) = PreferencesRepository(
        scope, Dispatchers.IO, PreferencesDao(settingsDataStore, defaultLocationId),
    )
}