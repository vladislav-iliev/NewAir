package com.vladislaviliev.newair.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.RuntimeData
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_graph, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lineChart = view.findViewById<LineChart>(R.id.line_chart)
        val appData: RuntimeData by activityViewModels()
        val sensors = appData.historySensors
        val dataSet = LineDataSet(sensors.indices.map { Entry(it.toFloat(), sensors[it].toFloat()) }, "Readings").apply {
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
            color = ContextCompat.getColor(requireContext(), R.color.semi_dark_grey)
            circleHoleColor = ContextCompat.getColor(requireContext(), R.color.light_grey)
            circleRadius = 6f
            circleHoleRadius = 5f
            lineWidth = 3f
            highLightColor = ContextCompat.getColor(requireContext(), R.color.text_on_grey)
        }

        lineChart.data = LineData(dataSet).apply {
            setValueTextSize(15f)
            setValueTextColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        }
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.axisRight.setDrawLabels(false)
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            labelRotationAngle = -45f
            valueFormatter = IndexAxisValueFormatter(createDates())
            setDrawGridLines(false)
        }
        lineChart.setVisibleYRange(0f, 140f, dataSet.axisDependency)
    }

    private fun createDates(): Array<String> {
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
            String.format(
                Locale.UK, "%d %s", calendar[Calendar.DAY_OF_MONTH], calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK)
            )
        }
    }
}