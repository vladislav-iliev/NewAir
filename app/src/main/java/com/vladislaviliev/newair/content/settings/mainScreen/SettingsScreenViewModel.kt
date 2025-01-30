package com.vladislaviliev.newair.content.settings.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val colorBlind =
        settingsRepository.isColorBlind.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun onColorBlindChanged(isColorBlind: Boolean) {
        viewModelScope.launch { settingsRepository.setIsColorBlind(isColorBlind) }
    }
}
