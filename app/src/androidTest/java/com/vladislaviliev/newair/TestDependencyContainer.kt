package com.vladislaviliev.newair

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.UserDatabase
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton
import com.vladislaviliev.newair.DependencyContainer as ProductionContainer
import com.vladislaviliev.newair.readings.downloader.metadata.PersistentDao as MetadataDao
import com.vladislaviliev.newair.user.settings.PersistentDao as SettingsDao

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ProductionContainer::class])
class TestDependencyContainer {

    @Provides
    @Singleton
    fun provideLocationsRepository(
        @ApplicationContext appContext: Context,
        scope: CoroutineScope
    ): UserLocationsRepository {
        val db = Room.inMemoryDatabaseBuilder(appContext, UserDatabase::class.java).build()
        val dispatcher = scope.coroutineContext[CoroutineDispatcher]!!
        return UserLocationsRepository(scope, dispatcher, db.userLocationDao())
    }

    @Provides
    @Singleton
    fun provideResponseRepository(
        @ApplicationContext appContext: Context,
        scope: CoroutineScope,
    ): ResponseRepository {

        val db = Room.inMemoryDatabaseBuilder(appContext, ReadingsDatabase::class.java).build()

        val dataStore = PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = { appContext.preferencesDataStoreFile("download_metadata_test") }
        )
        val dispatcher = scope.coroutineContext[CoroutineDispatcher]!!
        return ResponseRepository(
            scope,
            dispatcher,
            Downloader(),
            db.liveDao(),
            db.historyDao(),
            MetadataDao(dataStore)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext appContext: Context,
        scope: CoroutineScope,
    ): SettingsRepository {

        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("user_preferences") }
        )

        val dispatcher = scope.coroutineContext[CoroutineDispatcher]!!
        return SettingsRepository(scope, dispatcher, SettingsDao(dataStore))
    }
}