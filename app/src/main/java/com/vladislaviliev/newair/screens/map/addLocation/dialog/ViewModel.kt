package com.vladislaviliev.newair.screens.map.addLocation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val userRepository: UserLocationsRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle())
    val state = _state.asStateFlow()

    fun onAddLocationClick(name: String, lat: Double, lng: Double) {
        if (state.value is State.Loading) return
        viewModelScope.launch { addLocation(name, lat, lng) }
    }

    private suspend fun addLocation(name: String, lat: Double, lng: Double) {
        _state.emit(State.Loading())

        if (isNameNotAllowed(name)) {
            _state.emit(State.Error(R.string.invalid_name_x, name))
            return
        }
        if (userRepository.exists(name)) {
            _state.emit(State.Error(R.string.name_already_exists_x, name))
            return
        }
        userRepository.add(name, lat, lng)
        preferencesRepository.setCurrentLocation(userRepository.getLastId())
        _state.emit(State.Success(name))
    }

    private fun isNameNotAllowed(name: String) =
        name.startsWith(' ') || name.endsWith(' ') || name.isEmpty()
}