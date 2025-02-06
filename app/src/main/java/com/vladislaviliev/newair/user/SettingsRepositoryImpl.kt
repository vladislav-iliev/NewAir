package com.vladislaviliev.newair.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsRepositoryImpl(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    private val currentLocationIdKey = intPreferencesKey("CURRENT_LOCATION_ID")

    override val currentUserLocationId =
        dataStore.data.map { it[currentLocationIdKey] ?: DefaultUserLocation.value.id }

    override suspend fun setCurrentUserLocation(id: Int) {
        scope.launch(ioDispatcher) {
            dataStore.edit { it[currentLocationIdKey] = id }
        }.join()
    }

}