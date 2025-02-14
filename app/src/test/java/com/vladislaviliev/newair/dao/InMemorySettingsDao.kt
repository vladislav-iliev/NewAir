package com.vladislaviliev.newair.dao

import com.vladislaviliev.newair.user.settings.Dao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySettingsDao(defaultLocationId: Int) : Dao {

    private val _currentLocation = MutableStateFlow(defaultLocationId)

    override val currentLocation: Flow<Int> = _currentLocation

    override suspend fun setCurrentLocation(id: Int) {
        _currentLocation.emit(id)
    }
}