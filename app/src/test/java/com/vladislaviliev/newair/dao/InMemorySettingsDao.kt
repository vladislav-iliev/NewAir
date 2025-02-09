package com.vladislaviliev.newair.dao

import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.settings.Dao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySettingsDao : Dao {

    private val _currentLocation = MutableStateFlow(City.value.id)

    override val currentLocation: Flow<Int> = _currentLocation

    override suspend fun setCurrentLocation(id: Int) {
        _currentLocation.emit(id)
    }
}