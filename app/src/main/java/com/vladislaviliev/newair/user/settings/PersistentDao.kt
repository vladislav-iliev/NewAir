package com.vladislaviliev.newair.user.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vladislaviliev.newair.user.location.City
import kotlinx.coroutines.flow.map

class PersistentDao(private val dataStore: DataStore<Preferences>) : Dao {

    private val currentLocationIdKey = intPreferencesKey("CURRENT_LOCATION_ID")

    override val currentLocation = dataStore.data.map { it[currentLocationIdKey] ?: City.id }

    override suspend fun setCurrentLocation(id: Int) {
        dataStore.edit { it[currentLocationIdKey] = id }
    }
}