package com.vladislaviliev.newair.screens.settings.deleteAllDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val defaultLocationId: Int,
    private val userLocationsRepository: UserLocationsRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message = _message

    fun deleteAll() {
        viewModelScope.launch {
            preferencesRepository.setCurrentLocation(defaultLocationId)
            userLocationsRepository.deleteAllExceptCity()
            _message.emit("Done!")
        }
    }
}