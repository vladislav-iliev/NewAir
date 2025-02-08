package com.vladislaviliev.newair

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataRepository
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.UserDatabase
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import com.vladislaviliev.newair.DependencyContainer as ProductionContainer

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ProductionContainer::class])
class TestDependencyContainer {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideLocationsRepository(
        @ApplicationContext appContext: Context, scope: CoroutineScope,
    ): UserLocationsRepository {
        val db = Room.inMemoryDatabaseBuilder(appContext, UserDatabase::class.java).build()
        return UserLocationsRepository(scope, Dispatchers.IO, db.userLocationDao())
    }

    @Provides
    @Singleton
    fun provideResponseRepository(
        @ApplicationContext appContext: Context, scope: CoroutineScope,
    ): ResponseRepository {

        val metadataDataStore = PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("download_metadata") }
        )

        val db = Room.inMemoryDatabaseBuilder(appContext, ReadingsDatabase::class.java).build()

        return ResponseRepository(
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
            produceFile = { appContext.preferencesDataStoreFile("user_preferences_test") }
        )
        return SettingsRepository(scope, Dispatchers.IO, dataStore)
    }

}