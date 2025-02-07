package com.vladislaviliev.newair.dao

import com.vladislaviliev.newair.user.SettingsDao
import com.vladislaviliev.newair.user.location.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySettingsDao : SettingsDao {

    private val _currentLocation = MutableStateFlow(City.value.id)

    override val currentLocation: Flow<Int> = _currentLocation

    override suspend fun setCurrentLocation(id: Int) {
        _currentLocation.emit(id)
    }
}