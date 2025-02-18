package com.vladislaviliev.newair

import com.vladislaviliev.air.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.preferences.PreferencesRepository

data class TestRepoCollection(
    val locations: UserLocationsRepository,
    val readings: ResponseRepository,
    val preferences: PreferencesRepository
)