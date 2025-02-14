package com.vladislaviliev.newair.user.preferences

import kotlinx.coroutines.flow.Flow

interface Dao {
    val currentLocation: Flow<Int>
    suspend fun setCurrentLocation(id: Int)
}