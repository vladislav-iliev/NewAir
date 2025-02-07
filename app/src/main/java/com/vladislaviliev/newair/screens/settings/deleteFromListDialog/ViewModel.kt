package com.vladislaviliev.newair.screens.settings.deleteFromListDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.City
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val locationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var isDeleting = false

    val pagingFlow = locationsRepository.newPagingDelete()

    fun delete(ids: Collection<Int>) {
        if (isDeleting) return
        isDeleting = true

        viewModelScope.launch {
            if (settingsRepository.currentLocation.first() in ids)
                settingsRepository.setCurrentLocation(City.value.id)

            locationsRepository.delete(ids)
            isDeleting = false
        }
    }
}