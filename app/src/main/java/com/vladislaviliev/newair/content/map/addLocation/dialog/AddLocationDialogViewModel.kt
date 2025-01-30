package com.vladislaviliev.newair.content.map.addLocation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.content.map.addLocation.AddLocationState
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationDialogViewModel @Inject constructor(
    private val userRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val defaultState = AddLocationState(
        isAdding = false, successMessage = "", errorMessage = "",
    )

    private val _state = MutableStateFlow(defaultState)
    val state = _state.asStateFlow()

    fun onAddLocationClick(name: String, lat: Double, lng: Double) {
        viewModelScope.launch { addLocation(name, lat, lng) }
    }

    private suspend fun addLocation(name: String, lat: Double, lng: Double) {
        _state.emit(defaultState)

        if (userRepository.existsByName(name)) {
            _state.emit(AddLocationState(false, "", "\"$name\" already exists"))
            return
        }
        userRepository.addLocation(name, lat, lng)
        val newId = userRepository.getLastLocationId()
        settingsRepository.setCurrentUserLocation(newId)
        _state.emit(AddLocationState(false, "Successfully added $name", ""))
    }
}