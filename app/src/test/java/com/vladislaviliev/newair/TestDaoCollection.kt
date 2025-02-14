package com.vladislaviliev.newair

import com.vladislaviliev.newair.readings.history.HistoryDao
import com.vladislaviliev.newair.readings.live.LiveDao
import com.vladislaviliev.newair.readings.downloader.metadata.Dao as MetadataDao
import com.vladislaviliev.newair.user.location.Dao as UserLocationDao
import com.vladislaviliev.newair.user.settings.Dao as SettingsDao

data class TestDaoCollection(
    val locations: UserLocationDao,
    val live: LiveDao,
    val history: HistoryDao,
    val metadata: MetadataDao,
    val settings: SettingsDao
)