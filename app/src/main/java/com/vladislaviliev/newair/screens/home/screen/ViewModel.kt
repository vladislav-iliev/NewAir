package com.vladislaviliev.newair.screens.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.screens.home.screen.state.Transformer
import com.vladislaviliev.newair.user.location.LocationNotFoundException
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val defaultLocationId: Int,
    private val userLocationsRepository: UserLocationsRepository,
    private val responseRepository: ResponseRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val userLocation = settingsRepository.currentLocation
        .map(userLocationsRepository::get)
        .retry(predicate = ::whenDatabaseCannotFetchLocation)

    val screenState = combine(
        userLocation,
        responseRepository.liveResponses(),
        Transformer()::stateOf
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), Loading.value
    )

    private suspend fun whenDatabaseCannotFetchLocation(t: Throwable): Boolean {
        if (t !is LocationNotFoundException) return false
        settingsRepository.setCurrentLocation(defaultLocationId)
        return true
    }

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}