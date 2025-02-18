package com.vladislaviliev.newair

import com.vladislaviliev.air.readings.history.HistoryDao
import com.vladislaviliev.air.readings.live.LiveDao
import com.vladislaviliev.air.readings.downloader.metadata.Dao as MetadataDao
import com.vladislaviliev.newair.user.location.Dao as UserLocationDao
import com.vladislaviliev.newair.user.preferences.Dao as PreferencesDao

data class TestDaoCollection(
    val locations: UserLocationDao,
    val live: LiveDao,
    val history: HistoryDao,
    val metadata: MetadataDao,
    val preferences: PreferencesDao
)