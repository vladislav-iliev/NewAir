package com.vladislaviliev.newair.content.settings.deleteAllDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _isDone = MutableStateFlow(false)
    val isDone = _isDone

    fun deleteAll() {
        viewModelScope.launch {
            settingsRepository.setCurrentUserLocation(DefaultUserLocation.value.id)
            userLocationsRepository.deleteAll()
            _isDone.emit(true)
        }
    }
}