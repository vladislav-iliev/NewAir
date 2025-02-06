package com.vladislaviliev.newair

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.paging.PagingConfig
import androidx.room.Room
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataRepository
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepositoryImpl
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.SettingsRepositoryImpl
import com.vladislaviliev.newair.user.UserDatabase
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.UserLocationsRepositoryImpl
import com.vladislaviliev.newair.user.location.paging.PagingProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DependencyContainer {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideLocationsRepository(
        @ApplicationContext appContext: Context, scope: CoroutineScope,
    ): UserLocationsRepository {

        val db = Room.databaseBuilder(appContext, UserDatabase::class.java, "user_database").build()

        val pagingPageSize = 20
        val pagingPrefetchDistance = 10
        val pagingConfig = PagingConfig(
            pageSize = 10,
            initialLoadSize = pagingPageSize * 2,
            prefetchDistance = pagingPrefetchDistance,
            maxSize = 2 * pagingPrefetchDistance + pagingPageSize,
            enablePlaceholders = false,
        )
        val pagingProvider = PagingProvider(db.userLocationDao(), pagingConfig)

        return UserLocationsRepositoryImpl(
            scope, Dispatchers.IO, db.userLocationDao(), pagingProvider
        )
    }

    @Provides
    @Singleton
    fun provideResponseRepository(
        @ApplicationContext appContext: Context, scope: CoroutineScope,
    ): ResponseRepository {

        val metadataDataStore = PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("download_metadata") }
        )

        val db = Room.databaseBuilder(appContext, ReadingsDatabase::class.java, "readings_database")
            .build()

        return ResponseRepositoryImpl(
            scope,
            Dispatchers.IO,
            Downloader(),
            db.liveDao(),
            db.historyDao(),
            MetadataRepository(metadataDataStore)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext appContext: Context, scope: CoroutineScope
    ): SettingsRepository {
        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("user_preferences") }
        )
        return SettingsRepositoryImpl(scope, Dispatchers.IO, dataStore)
    }
}