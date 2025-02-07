package com.vladislaviliev.newair

import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.City
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySettingsRepository : SettingsRepository {

    private val _currentUserLocationId = MutableStateFlow(City.value.id)

    override val currentUserLocationId = _currentUserLocationId

    override suspend fun setCurrentUserLocation(id: Int) {
        _currentUserLocationId.emit(id)
    }
}