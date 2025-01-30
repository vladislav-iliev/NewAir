package com.vladislaviliev.newair.readings.downloader.metadata

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class MetadataRepository(private val dataStore: DataStore<Preferences>) {
    private val errorMsgKey = stringPreferencesKey("ERROR_MSG")
    private val timestampKey = stringPreferencesKey("TIMESTAMP")

    val errorMsg = dataStore.data.map { it[errorMsgKey] ?: MetadataConstants.METADATA_NOT_FOUND }
    val timestamp = dataStore.data.map { it[timestampKey] ?: MetadataConstants.METADATA_NOT_FOUND }

    suspend fun storeData(errorMsg: String, timestamp: String) {
        dataStore.edit { it[errorMsgKey] = errorMsg }
        dataStore.edit { it[timestampKey] = timestamp }
    }
}