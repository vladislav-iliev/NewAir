package com.vladislaviliev.newair.screens.map.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.air.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.screens.map.reading.state.Loading
import com.vladislaviliev.newair.screens.map.reading.state.Transformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ViewModel @Inject constructor(
    private val responseRepository: ResponseRepository,
) : ViewModel() {

    val state = responseRepository.liveResponses().map(Transformer()::stateOf).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), Loading.value
    )

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}