package com.vladislaviliev.newair.readings.downloader.responses

import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataRepository
import com.vladislaviliev.newair.readings.history.HistoryDao
import com.vladislaviliev.newair.readings.live.LiveDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class ResponseRepositoryImpl(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher,
    private val downloader: Downloader,
    private val liveDao: LiveDao,
    private val historyDao: HistoryDao,
    private val metadataRepository: MetadataRepository,
) : ResponseRepository {
    /** Database and DataStore could emit very quickly after each other and
     * bombard the receivers with fast data. The determined purpose is to
     * collect both metadata and stored readings at once, if possible **/
    private val flowDebounceMillis = 200L

    private val isLoading = MutableStateFlow(false)

    @OptIn(FlowPreview::class)
    override fun liveResponses() = combine(
        isLoading,
        liveDao.getAllFlow(),
        metadataRepository.flow,
        ::LiveResponse
    ).debounce(flowDebounceMillis)

    @OptIn(FlowPreview::class)
    override fun historyResponses() = combine(
        isLoading,
        historyDao.getAllFlow(),
        metadataRepository.flow,
        ::HistoryResponse
    ).debounce(flowDebounceMillis)

    override suspend fun refresh() = if (isLoading.value) Unit else scope.launch(ioDispatcher) {
        isLoading.emit(true)

        liveDao.deleteAll()
        historyDao.deleteAll()
        metadataRepository.clear()

        val download = downloader.download()

        liveDao.upsert(download.liveReadings)
        historyDao.upsert(download.historyReadings)
        metadataRepository.store(download.metadata)

        isLoading.emit(false)
    }.join()
}