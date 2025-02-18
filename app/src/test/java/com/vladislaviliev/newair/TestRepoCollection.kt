package com.vladislaviliev.newair

import com.vladislaviliev.air.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.air.user.location.UserLocationsRepository
import com.vladislaviliev.air.user.preferences.PreferencesRepository

data class TestRepoCollection(
    val locations: UserLocationsRepository,
    val readings: ResponseRepository,
    val preferences: PreferencesRepository
)