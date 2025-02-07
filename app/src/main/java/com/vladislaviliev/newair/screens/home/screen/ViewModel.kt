package com.vladislaviliev.newair.screens.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.screens.home.screen.state.Transformer
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.UserLocationsRepository
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
        Transformer::stateOf
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), Loading.value
    )

    private suspend fun whenDatabaseCannotFetchLocation(t: Throwable): Boolean {
        if (t !is NoSuchElementException) return false
        userLocationsRepository.addInitial() // will trigger on fresh installs
        settingsRepository.setCurrentLocation(City.value.id)
        return true
    }

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}