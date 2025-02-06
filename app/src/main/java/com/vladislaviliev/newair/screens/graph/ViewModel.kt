package com.vladislaviliev.newair.screens.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.screens.graph.state.Loading
import com.vladislaviliev.newair.screens.graph.state.Transformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val responseRepository: ResponseRepository
) : ViewModel() {

    val state = responseRepository.historyResponses()
        .map(Transformer::stateOf)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), Loading.value)

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}