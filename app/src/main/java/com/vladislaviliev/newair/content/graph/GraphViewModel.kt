package com.vladislaviliev.newair.content.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.readings.ResponseRepository
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponseAppStartup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val responseRepository: ResponseRepository,
) : ViewModel() {

    val state = responseRepository.historyResponses().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        HistoryResponseAppStartup.value
    )

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}