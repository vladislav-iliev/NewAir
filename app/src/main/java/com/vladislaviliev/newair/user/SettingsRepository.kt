package com.vladislaviliev.newair.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataStore: DataStore<Preferences>,
) {

    private val currentLocationIdKey = intPreferencesKey("CURRENT_LOCATION_ID")
    private val colorBlindPrefKey = booleanPreferencesKey("IS_COLOR_BLIND")

    val currentUserLocationId =
        dataStore.data.map { it[currentLocationIdKey] ?: DefaultUserLocation.value.id }
    val isColorBlind =
        dataStore.data.map { it[colorBlindPrefKey] == true }

    suspend fun setCurrentUserLocation(id: Int) {
        scope.launch(ioDispatcher) {
            dataStore.edit { it[currentLocationIdKey] = id }
        }.join()
    }

    suspend fun setIsColorBlind(isColorBlind: Boolean) {
        scope.launch(ioDispatcher) {
            dataStore.edit { it[colorBlindPrefKey] = isColorBlind }
        }.join()
    }
}