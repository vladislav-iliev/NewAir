package com.vladislaviliev.newair.screens.home.locationPicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Transformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    locationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository,
    pagingConfig: PagingConfig,
) : ViewModel() {

    private val _hasSelected = MutableStateFlow(false)
    val hasSelected = _hasSelected.asStateFlow()

    val pagingFlow = Pager(
        pagingConfig, pagingSourceFactory = locationsRepository::newPagingSourceSelect
    ).flow.map(Transformer::transform)

    fun onLocationSelected(id: Int) {
        viewModelScope.launch {
            settingsRepository.setCurrentLocation(id)
            _hasSelected.emit(true)
        }
    }

}