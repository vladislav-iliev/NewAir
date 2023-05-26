package com.vladislaviliev.newair.fragments.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.Vm
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GraphFragment : Fragment() {
    private val dates = createDates()
    private lateinit var lineChart: LineChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_graph, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = view.findViewById(R.id.line_chart)
        setUpLineChart()
        updateGraph()
    }

    private fun createDates(): Array<Date> {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        return Array(7) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            dateNullifyTime(calendar.time)
        }
    }

    private fun dateNullifyTime(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }

    private fun initializeEntries(): List<Entry> {
        val entryList: MutableList<Entry> = ArrayList()
        for (i in dates.indices) entryList.add(Entry(i.toFloat(), (-1).toFloat()))
        return entryList
    }

    private fun initializeDataSet(entryList: List<Entry>): LineDataSet {
        val dataSet = LineDataSet(entryList, resources.getString(R.string.line_data_set_label))
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.semi_dark_grey)
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        dataSet.circleHoleColor = ContextCompat.getColor(requireContext(), R.color.light_grey)
        dataSet.circleRadius = 6.toFloat()
        dataSet.circleHoleRadius = 5.toFloat()
        dataSet.lineWidth = 3.toFloat()
        dataSet.highLightColor = ContextCompat.getColor(requireContext(), R.color.text_on_grey)
        return dataSet
    }

    private fun initializeDataSetList(lineDataSet: LineDataSet): List<ILineDataSet> {
        val dataSetList: MutableList<ILineDataSet> = ArrayList()
        dataSetList.add(lineDataSet)
        return dataSetList
    }

    private fun initializeLineData(dataSetList: List<ILineDataSet>): LineData {
        val lineData = LineData(dataSetList)
        lineData.setValueTextSize(15.toFloat())
        lineData.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        return lineData
    }

    private fun setUpLineChart() {
        val entryList = initializeEntries()
        val lineDataSet = initializeDataSet(entryList)
        val lineDataSetList = initializeDataSetList(lineDataSet)
        val lineData = initializeLineData(lineDataSetList)
        lineChart.data = lineData
        lineChart.legend.isEnabled = false
        lineChart.axisRight.setDrawLabels(false)
        lineChart.setVisibleYRange(0f, 140.toFloat(), lineDataSet.axisDependency)
        initializeDescription(lineChart)
        initializeXAxis(lineChart)
    }

    private fun initializeDescription(lineChart: LineChart?) {
        val description = Description()
        description.textSize = 12.toFloat()
        description.textColor = ContextCompat.getColor(requireContext(), R.color.dark_grey)
        description.text = "PM10"
        lineChart!!.description = description
    }

    private fun initializeXAxis(lineChart: LineChart?) {
        val xAxis = lineChart!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return formatDate(dates[dates.size - value.toInt() - 1])
            }
        }
        xAxis.labelRotationAngle = (-45).toFloat()
    }

    private fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return String.format(
            Locale.UK,
            getString(R.string.date_format),
            calendar[Calendar.DAY_OF_MONTH],
            calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK)
        )
    }

    private fun updateGraph() {
        val viewModel: Vm by activityViewModels()
        val lineDataSet = lineChart.lineData.getDataSetByIndex(0)
        val pm10 = viewModel.historySensors
        (0 until lineDataSet.entryCount).forEach {
            lineDataSet.getEntryForIndex(it).y = pm10[it].toFloat()
        }
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }
}