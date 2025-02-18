package com.vladislaviliev.newair.screens.settings.deleteFromListDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vladislaviliev.air.user.location.UserLocationsRepository
import com.vladislaviliev.air.user.preferences.PreferencesRepository
import com.vladislaviliev.newair.screens.paging.Transformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val defaultLocationId: Int,
    private val locationsRepository: UserLocationsRepository,
    private val preferencesRepository: PreferencesRepository,
    pagingConfig: PagingConfig,
) : ViewModel() {

    private var isDeleting = AtomicBoolean(false)

    val pagingFlow = Pager(pagingConfig) { locationsRepository.newPagingSource(defaultLocationId) }
        .flow.map(Transformer()::transform)

    fun delete(ids: Collection<Int>) {
        if (!isDeleting.compareAndSet(false, true)) return

        viewModelScope.launch {
            if (preferencesRepository.currentLocation.first() in ids)
                preferencesRepository.setCurrentLocation(defaultLocationId)

            locationsRepository.delete(ids)
        }.invokeOnCompletion { isDeleting.set(false) }
    }
}