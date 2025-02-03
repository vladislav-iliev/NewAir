package com.vladislaviliev.newair.screens.map.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.screens.map.reading.state.Loading
import com.vladislaviliev.newair.screens.map.reading.state.Transformer
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ViewModel @Inject constructor(
    private val responseRepository: ResponseRepository, settingsRepository: SettingsRepository,
) : ViewModel() {

    val state = combine(
        settingsRepository.isColorBlind,
        responseRepository.liveResponses(),
        Transformer::stateOf
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), Loading.value
    )

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}