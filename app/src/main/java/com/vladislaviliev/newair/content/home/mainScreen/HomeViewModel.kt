package com.vladislaviliev.newair.content.home.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.content.home.mainScreen.state.HomeScreenStateLoading
import com.vladislaviliev.newair.content.home.mainScreen.state.HomeScreenStateTransformers
import com.vladislaviliev.newair.readings.downloader.responses.ResponseRepository
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.DefaultUserLocation
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
class HomeViewModel @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository,
    private val responseRepository: ResponseRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val userLocation = settingsRepository.currentUserLocationId
        .map(userLocationsRepository::getLocation)
        .retry(predicate = ::whenDatabaseCannotFetchLocation)

    val screenState = combine(
        settingsRepository.isColorBlind,
        userLocation,
        responseRepository.liveResponses(),
        HomeScreenStateTransformers::stateOf
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenStateLoading.value
    )

    private suspend fun whenDatabaseCannotFetchLocation(t: Throwable): Boolean {
        if (t !is NoSuchElementException) return false
        userLocationsRepository.addInitial() // will trigger on fresh installs
        settingsRepository.setCurrentUserLocation(DefaultUserLocation.value.id)
        return true
    }

    fun onRefreshClick() {
        viewModelScope.launch { responseRepository.refresh() }
    }
}