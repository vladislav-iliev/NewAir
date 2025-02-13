package com.vladislaviliev.newair

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.vladislaviliev.newair.dependencies.Container
import com.vladislaviliev.newair.dependencies.PreferencesDependency
import com.vladislaviliev.newair.dependencies.ReadingsDependency
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.user.UserDatabase
import com.vladislaviliev.newair.user.location.PrepopulateDatabase
import com.vladislaviliev.newair.user.location.UserLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [Container::class])
class TestDependencyContainer {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    fun providesCity() = UserLocation(1, "City", 0.0, 0.0)

    @Provides
    @Singleton
    fun provideLocationsDatabase(
        @ApplicationContext appContext: Context,
        preloadedUserLocation: UserLocation,
    ) = Room.inMemoryDatabaseBuilder(appContext, UserDatabase::class.java)
        .addCallback(PrepopulateDatabase(preloadedUserLocation))
        .build()

    @Provides
    @Singleton
    fun provideReadingsDatabase(@ApplicationContext appContext: Context) =
        Room.inMemoryDatabaseBuilder(appContext, ReadingsDatabase::class.java).build()

    @Provides
    @Singleton
    @ReadingsDependency
    fun providesDownloadMetadataDataStore(@ApplicationContext appContext: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("download_metadata_test") }
        )

    @Provides
    @Singleton
    @PreferencesDependency
    fun providesPreferencesDataStore(@ApplicationContext appContext: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("preferences_test") }
        )
}