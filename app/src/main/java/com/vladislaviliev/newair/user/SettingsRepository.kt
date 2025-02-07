package com.vladislaviliev.newair.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vladislaviliev.newair.user.location.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>,
): SettingsDao {

    private val currentLocationIdKey = intPreferencesKey("CURRENT_LOCATION_ID")

    override val currentLocation =
        dataStore.data.map { it[currentLocationIdKey] ?: City.value.id }

    override suspend fun setCurrentLocation(id: Int) {
        scope.launch(ioDispatcher) {
            dataStore.edit { it[currentLocationIdKey] = id }
        }.join()
    }
}