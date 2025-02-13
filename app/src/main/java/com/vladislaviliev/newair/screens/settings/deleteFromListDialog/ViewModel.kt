package com.vladislaviliev.newair.screens.settings.deleteFromListDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.location.paging.Transformer
import com.vladislaviliev.newair.user.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val cityId: Int,
    private val locationsRepository: UserLocationsRepository,
    private val settingsRepository: SettingsRepository,
    pagingConfig: PagingConfig,
) : ViewModel() {

    private var isDeleting = AtomicBoolean(false)

    val pagingFlow = Pager(
        pagingConfig, pagingSourceFactory = locationsRepository::newPagingSourceDelete
    ).flow.map(Transformer::transform)

    fun delete(ids: Collection<Int>) {
        if (!isDeleting.compareAndSet(false, true)) return

        viewModelScope.launch {
            if (settingsRepository.currentLocation.first() in ids)
                settingsRepository.setCurrentLocation(cityId)

            locationsRepository.delete(ids)
        }.invokeOnCompletion { isDeleting.set(false) }
    }
}