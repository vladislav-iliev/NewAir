package com.vladislaviliev.newair.screens.graph.uiComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData
import com.vladislaviliev.newair.readings.history.HistoryReading
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun Graph(readings: Iterable<HistoryReading>, modifier: Modifier = Modifier) {

    val dates = createDates()

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
    position: Int, dates: Array<String>, readings: Iterable<HistoryReading>
): PointData {
    val daysBefore = 7 - position - 1
    val pollution = readings.first { it.daysBefore == daysBefore }.measure.toFloat()
    return PointData(pollution, dates[position])
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