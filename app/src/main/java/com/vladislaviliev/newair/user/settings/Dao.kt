package com.vladislaviliev.newair.user.settings

import kotlinx.coroutines.flow.Flow

interface Dao {
    val currentLocation: Flow<Int>
    suspend fun setCurrentLocation(id: Int)
}