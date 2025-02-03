package com.vladislaviliev.newair.readings.downloader.metadata

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class MetadataRepository(private val dataStore: DataStore<Preferences>) {

    private val encodeConcat = "~"
    private val prefsKey = stringPreferencesKey("METADATA_KEY")

    val flow = dataStore.data.map(::decode)

    suspend fun store(metadata: Metadata) {
        dataStore.edit { it[prefsKey] = encode(metadata) }
    }

    suspend fun clear() {
        dataStore.edit(MutablePreferences::clear)
    }

    private fun encode(metadata: Metadata) = metadata.errorMsg + encodeConcat + metadata.timestamp

    private fun decode(str: String) = str.split(encodeConcat).let { Metadata(it[0], it[1]) }

    private fun decode(prefs: Preferences): Metadata {
        val str = prefs[prefsKey]
        if (str == null) return MetadataNotFound.value
        return decode(str)
    }
}