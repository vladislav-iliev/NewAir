package com.vladislaviliev.newair.settings.deleteAllDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun deleteAll() {
        viewModelScope.launch {
            settingsRepository.setCurrentUserLocation(DefaultUserLocation.value.id)
            userLocationsRepository.deleteAll()
        }
    }
}