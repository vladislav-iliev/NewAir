package com.vladislaviliev.newair.content.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponse
import com.vladislaviliev.newair.readings.downloader.responses.HistoryResponseAppStartup
import com.vladislaviliev.newair.readings.history.HistoryReading
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
internal fun GraphScreen(
    response: HistoryResponse,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier.fillMaxSize()) {
        Column {
            GraphTopAppBar(onRefreshClick)
            Content(response)
        }
    }
}

@Composable
private fun Content(response: HistoryResponse, modifier: Modifier = Modifier) {
    if (response.readings.isEmpty()) {
        Text("No data", modifier)
        return
    }
    if (response.errorMsg.isNotBlank()) {
        Text(response.errorMsg, modifier)
        return
    }
    Graph(createDates(), response.readings, modifier)
}

@Composable
private fun Graph(
    dates: Array<String>,
    readings: Iterable<HistoryReading>,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Spacer(Modifier.weight(0.05f))
        PointChart(
            List(7) { sequenceToPointData(it, dates, readings) }.toChartDataCollection(),
            MaterialTheme.colorScheme.onSurface,
            Modifier.weight(0.9f),
            MaterialTheme.colorScheme.surface,
        )
        Spacer(Modifier.weight(0.05f))
    }
}

private fun sequenceToPointData(
    i: Int,
    dates: Array<String>,
    readings: Iterable<HistoryReading>
): PointData {
    val daysBefore = 7 - i - 1
    val pollution = readings.first { it.daysBefore == daysBefore }.measure.toFloat()
    return PointData(pollution, dates[i])
}

private fun createDates(): Array<String> {
    val weekdays = DateFormatSymbols(Locale.getDefault()).shortWeekdays
    val calendar = Calendar.getInstance().apply {
        time = Date()
        this[Calendar.HOUR_OF_DAY] = 0
        this[Calendar.MINUTE] = 0
        this[Calendar.SECOND] = 0
        this[Calendar.MILLISECOND] = 0
        add(Calendar.DAY_OF_YEAR, -7)
    }
    return Array(7) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        weekdays[calendar.get(Calendar.DAY_OF_WEEK)]
    }
}

@Preview(showSystemUi = true, device = "id:pixel_4")
@Composable
private fun ScreenPreviewGraph() {
    GraphScreen(
        HistoryResponseAppStartup.value,
        {},
        Modifier.fillMaxSize()
    )
}