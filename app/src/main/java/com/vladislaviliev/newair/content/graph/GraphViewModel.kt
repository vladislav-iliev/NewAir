package com.vladislaviliev.newair.content.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.content.graph.state.GraphStateLoading
import com.vladislaviliev.newair.content.graph.state.GraphStateTransformers
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val responseRepository: ResponseRepository
) : ViewModel() {

    val state = responseRepository.historyResponses()
        .map(GraphStateTransformers::stateOf)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), GraphStateLoading.value)

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}