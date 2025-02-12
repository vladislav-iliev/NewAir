package com.vladislaviliev.newair.dependencies

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.vladislaviliev.newair.readings.ReadingsDatabase
import com.vladislaviliev.newair.user.UserDatabase
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
class Container {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideLocationsDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, UserDatabase::class.java, "user_database").build()

    @Provides
    @Singleton
    fun provideReadingsDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, ReadingsDatabase::class.java, "readings_database").build()

    @Provides
    @Singleton
    @ReadingsDependency
    fun providesDownloadMetadataDataStore(@ApplicationContext appContext: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("download_metadata") }
        )

    @Provides
    @Singleton
    @PreferencesDependency
    fun providesPreferencesDataStore(@ApplicationContext appContext: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("preferences") }
        )
}