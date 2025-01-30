package com.vladislaviliev.newair.content.map.readingMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.readings.ResponseRepository
import com.vladislaviliev.newair.user.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ReadingMapViewModel @Inject constructor(
    private val responseRepository: ResponseRepository, settingsRepository: SettingsRepository,
) : ViewModel() {

    val state = combine(
        settingsRepository.isColorBlind, responseRepository.liveResponses(), ::ReadingMapState
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ReadingMapStateAppStartup.value
    )

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}