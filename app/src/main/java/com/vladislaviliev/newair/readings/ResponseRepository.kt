package com.vladislaviliev.newair.readings

import com.vladislaviliev.newair.readings.downloader.Downloader
import com.vladislaviliev.newair.readings.downloader.metadata.MetadataRepository
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponse
import com.vladislaviliev.newair.readings.downloader.responses.LiveResponse
import com.vladislaviliev.newair.readings.history.HistoryDao
import com.vladislaviliev.newair.readings.live.LiveDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class ResponseRepository(
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val downloader: Downloader,
    private val liveDao: LiveDao,
    private val historyDao: HistoryDao,
    private val metadataRepository: MetadataRepository,
) {
    /** Database and DataStore emit very quickly after each other
     * and cause fast re-emissions and recompositions **/
    private val flowDebounceMillis = 200L

    @OptIn(FlowPreview::class)
    fun liveResponses() = combine(
        liveDao.getAllFlow(),
        metadataRepository.errorMsg,
        metadataRepository.timestamp,
        ::LiveResponse
    ).debounce(flowDebounceMillis)

    @OptIn(FlowPreview::class)
    fun historyResponses() = combine(
        historyDao.getAllFlow(),
        metadataRepository.errorMsg,
        metadataRepository.timestamp,
        ::HistoryResponse
    ).debounce(flowDebounceMillis)

    suspend fun refresh() = scope.launch(ioDispatcher) {
        val response = downloader.newResponse()

        liveDao.deleteAll()
        liveDao.upsert(response.liveReadings)

        historyDao.deleteAll()
        historyDao.upsert(response.historyReadings)

        metadataRepository.storeData(response.errorMsg, response.timestamp)
    }.join()
}