package com.vladislaviliev.newair.content.settings.deleteFromListDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.newair.user.SettingsRepository
import com.vladislaviliev.newair.user.location.DefaultUserLocation
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteFromListViewModel @Inject constructor(
    private val locationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var isDeleting = false

    val pagingFlow = locationsRepository.pagingFlowDelete()

    fun delete(ids: Collection<Int>) {
        if (isDeleting) return
        isDeleting = true

        viewModelScope.launch {
            if (settingsRepository.currentUserLocationId.first() in ids)
                settingsRepository.setCurrentUserLocation(DefaultUserLocation.value.id)

            locationsRepository.deleteLocations(ids)
            isDeleting = false
        }
    }
}