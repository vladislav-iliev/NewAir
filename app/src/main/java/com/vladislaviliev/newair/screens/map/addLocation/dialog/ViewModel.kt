package com.vladislaviliev.newair.screens.map.addLocation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.screens.map.addLocation.screen.State
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val userRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val defaultState = State(
        isAdding = false, successMessage = "", errorMessage = "",
    )

    private val _state = MutableStateFlow(defaultState)
    val state = _state.asStateFlow()

    fun onAddLocationClick(name: String, lat: Double, lng: Double) {
        viewModelScope.launch { addLocation(name, lat, lng) }
    }

    private suspend fun addLocation(name: String, lat: Double, lng: Double) {
        _state.emit(defaultState)

        if (userRepository.exists(name)) {
            _state.emit(State(false, "", "\"$name\" already exists"))
            return
        }
        userRepository.add(name, lat, lng)
        val newId = userRepository.getLastId()
        settingsRepository.setCurrentLocation(newId)
        _state.emit(State(false, "Successfully added $name", ""))
    }
}