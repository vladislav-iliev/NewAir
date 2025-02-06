package com.vladislaviliev.newair

import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySettingsRepository : SettingsRepository {

    private val _currentUserLocationId = MutableStateFlow(DefaultUserLocation.value.id)

    override val currentUserLocationId = _currentUserLocationId

    override suspend fun setCurrentUserLocation(id: Int) {
        _currentUserLocationId.emit(id)
    }
}