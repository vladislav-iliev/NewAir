package com.vladislaviliev.newair.user

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val currentUserLocationId: Flow<Int>
    suspend fun setCurrentUserLocation(id: Int)
}