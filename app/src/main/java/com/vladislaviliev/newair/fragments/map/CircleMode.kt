package com.vladislaviliev.newair.fragments.map

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.vladislaviliev.newair.Vm
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.data.Sensor
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions

internal class CircleMode(fragment: MapFragment, private val googleMap: GoogleMap) {

    @ColorInt
    private val colors = fragment.resources.getIntArray(R.array.colors)

    @ColorInt
    private val colorsColorblind = fragment.resources.getIntArray(R.array.colors_colorblind)
    private val colorStartValues = fragment.resources.getIntArray(R.array.color_dividers_int)
    private val legendBackground = fragment.requireView().findViewById<View>(R.id.legendBackground)
    private val legendBoxes = mutableListOf<View>()
    private val legendTexts = mutableListOf<View>()
    private val isColorBlind = PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())
        .getBoolean(fragment.resources.getString(R.string.color_blind_switch_key), false)

    init {
        initializeLegend(fragment.requireView())

        val viewModel: Vm by (fragment.activityViewModels())
        fragment.requireView().findViewById<View>(R.id.refreshButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { viewModel.downloadData() }
        }
        fragment.requireView().findViewById<View>(R.id.legendButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { toggleMapLegend() }
        }
        viewModel.liveSensors.observe(fragment) { onNewData(it) }
    }

    private fun onNewData(sensors: Iterable<Sensor>) {
        googleMap.clear()
        val circleOptions = CircleOptions().radius(100.toDouble()).strokeWidth(0.toFloat())
        sensors.filter { it.type == Sensor.SensorType.PM10 }.forEach {
            circleOptions.center(it.latLng)
            circleOptions.fillColor(createColor(it.measure))
            googleMap.addCircle(circleOptions)
        }
    }

    @ColorInt
    private fun createColor(pollution: Double): Int {
        var colorIdx = colorStartValues.indexOfFirst { pollution <= it }
        if (colorIdx < 0) colorIdx = colorStartValues.lastIndex
        val newColor = if (isColorBlind) colorsColorblind[colorIdx] else colors[colorIdx]
        return Color.argb(150, Color.red(newColor), Color.green(newColor), Color.blue(newColor))
    }

    private fun initializeLegend(root: View) {
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
        legendTexts.add(root.findViewById(R.id.measurement))
        legendBoxes.indices.forEach { legendBoxes[it].setBackgroundColor(if (isColorBlind) colorsColorblind[it] else colors[it]) }
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