package com.example.newair.fragments.map

import android.graphics.Color
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.example.newair.R
import com.example.newair.data.SensorViewModel
import com.example.newair.data.sensors.Sensor
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions

internal class CircleMode(private val fragment: MapFragment, private val googleMap: GoogleMap) {

    private val colors = fragment.resources.getIntArray(R.array.colors)
    private val colorsColorblind = fragment.resources.getIntArray(R.array.colors_colorblind)
    private val colorStartValues = fragment.resources.getIntArray(R.array.color_dividers_int)
    private val isColorBlind = PreferenceManager
        .getDefaultSharedPreferences(fragment.requireContext())
        .getBoolean(fragment.getString(R.string.color_blind_switch_key), false)

    private var legendBackground = fragment.requireView().findViewById<View>(R.id.legendBackground)
    private var legendBoxes = mutableListOf<View>()
    private var legendTexts = mutableListOf<View>()

    init {
        initializeLegend()
        setMapLegendColors(isColorBlind)
        fragment.requireView().findViewById<View>(R.id.refreshButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { onRefreshPressed() }
        }
        fragment.requireView().findViewById<View>(R.id.legendButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { toggleMapLegend() }
        }
        onRefreshPressed()
    }

    private fun onRefreshPressed() {
        googleMap.clear()
        val viewModel: SensorViewModel by (fragment.activityViewModels())
        val circleOptions = CircleOptions().radius(100.toDouble()).strokeWidth(0.toFloat())

        viewModel.downloadData()
        viewModel.uiState.value.liveData.filter { it.type == Sensor.SensorType.PM10 }.forEach {
            circleOptions.center(it.latLng)
            circleOptions.fillColor(getCircleColor(it.measure))
            googleMap.addCircle(circleOptions)
        }
    }

    private fun getCircleColor(pollution: Double): Int {
        var i = 1
        while (i < colorStartValues.size) {
            if (pollution < colorStartValues[i]) break
            i++
        }
        val newColor = if (isColorBlind) colorsColorblind[i] else colors[i]
        return Color.argb(150, Color.red(newColor), Color.green(newColor), Color.blue(newColor))
    }

    private fun initializeLegend() {
        val root = fragment.requireView()
        legendBackground = root.findViewById(R.id.legendBackground)
        legendBoxes.add(root.findViewById(R.id.legendBlue))
        legendBoxes.add(root.findViewById(R.id.legendGreenLight))
        legendBoxes.add(root.findViewById(R.id.legendGreen))
        legendBoxes.add(root.findViewById(R.id.legendGreenDark))
        legendBoxes.add(root.findViewById(R.id.legendOrangeLight))
        legendBoxes.add(root.findViewById(R.id.legendOrange))
        legendBoxes.add(root.findViewById(R.id.legendOrangeDark))
        legendBoxes.add(root.findViewById(R.id.legendRedLight))
        legendBoxes.add(root.findViewById(R.id.legendRed))
        legendBoxes.add(root.findViewById(R.id.legendRedDark))
        legendBoxes.add(root.findViewById(R.id.legendPurple))
        legendTexts.add(root.findViewById(R.id.legendTextBlue))
        legendTexts.add(root.findViewById(R.id.legendTextGreenLight))
        legendTexts.add(root.findViewById(R.id.legendTextGreen))
        legendTexts.add(root.findViewById(R.id.legendTextGreenDark))
        legendTexts.add(root.findViewById(R.id.legendTextOrangeLight))
        legendTexts.add(root.findViewById(R.id.legendTextOrange))
        legendTexts.add(root.findViewById(R.id.legendTextOrangeDark))
        legendTexts.add(root.findViewById(R.id.legendTextRedLight))
        legendTexts.add(root.findViewById(R.id.legendTextRed))
        legendTexts.add(root.findViewById(R.id.legendTextRedDark))
        legendTexts.add(root.findViewById(R.id.legendTextPurple))
    }

    private fun setMapLegendColors(isColorBlind: Boolean) {
        legendBoxes.forEach {
            val boxIdx = legendBoxes.indexOf(it)
            it.setBackgroundColor(if (isColorBlind) colorsColorblind[boxIdx] else colors[boxIdx])
        }
    }

    private fun toggleMapLegend() {
        if (legendBackground.visibility == View.VISIBLE) {
            legendBackground.visibility = View.GONE
            legendBoxes.forEach { it.visibility = View.GONE }
            legendTexts.forEach { it.visibility = View.GONE }
            return
        }
        legendBackground.visibility = View.VISIBLE
        legendBoxes.forEach { it.visibility = View.VISIBLE }
        legendTexts.forEach { it.visibility = View.VISIBLE }
    }
}