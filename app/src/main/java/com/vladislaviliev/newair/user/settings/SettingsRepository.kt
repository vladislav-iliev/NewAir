package com.vladislaviliev.newair.user.settings

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SettingsRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: Dao,
) {
    val currentLocation = dao.currentLocation

    suspend fun setCurrentLocation(id: Int) {
        scope.launch(ioDispatcher) { dao.setCurrentLocation(id) }.join()
    }
}