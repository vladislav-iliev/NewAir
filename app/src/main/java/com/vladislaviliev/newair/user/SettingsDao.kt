package com.vladislaviliev.newair.user

import kotlinx.coroutines.flow.Flow

interface SettingsDao {
    val currentLocation: Flow<Int>
    suspend fun setCurrentLocation(id: Int)
}