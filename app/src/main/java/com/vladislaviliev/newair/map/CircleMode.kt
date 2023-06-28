package com.vladislaviliev.newair.map

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.RuntimeData
import com.vladislaviliev.newair.sensor.Health
import com.vladislaviliev.newair.sensor.SensorType

internal class CircleMode(fragment: Fragment, googleMap: GoogleMap) {

    init {
        val data: RuntimeData by (fragment.activityViewModels())
        val root = fragment.requireView()
        val legendContainer = root.findViewById<View>(R.id.legend_container)
        val isColorBlind = PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())
            .getBoolean(fragment.resources.getString(R.string.color_blind_switch_key), false)
        root.findViewById<View>(R.id.legendBlue).setBackgroundColor(Health.getColor(isColorBlind, 0))
        root.findViewById<View>(R.id.legendGreenLight).setBackgroundColor(Health.getColor(isColorBlind, 1))
        root.findViewById<View>(R.id.legendGreen).setBackgroundColor(Health.getColor(isColorBlind, 2))
        root.findViewById<View>(R.id.legendGreenDark).setBackgroundColor(Health.getColor(isColorBlind, 3))
        root.findViewById<View>(R.id.legendOrangeLight).setBackgroundColor(Health.getColor(isColorBlind, 4))
        root.findViewById<View>(R.id.legendOrange).setBackgroundColor(Health.getColor(isColorBlind, 5))
        root.findViewById<View>(R.id.legendOrangeDark).setBackgroundColor(Health.getColor(isColorBlind, 6))
        root.findViewById<View>(R.id.legendRedLight).setBackgroundColor(Health.getColor(isColorBlind, 7))
        root.findViewById<View>(R.id.legendRed).setBackgroundColor(Health.getColor(isColorBlind, 8))
        root.findViewById<View>(R.id.legendRedDark).setBackgroundColor(Health.getColor(isColorBlind, 9))
        root.findViewById<View>(R.id.legendPurple).setBackgroundColor(Health.getColor(isColorBlind, 10))

        root.findViewById<View>(R.id.refreshButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { data.download() }
        }
        root.findViewById<View>(R.id.legendButton).apply {
            visibility = View.VISIBLE
            setOnClickListener { legendContainer.visibility = if (legendContainer.isVisible) View.GONE else View.VISIBLE }
        }

        val circleBuilder = CircleOptions().radius(100.toDouble()).strokeWidth(0.toFloat())
        data.liveSensors.observe(fragment) { sensors ->
            googleMap.clear()
            sensors.filter { it.type == SensorType.PM10 }.forEach {
                circleBuilder.center(it.latLng)
                circleBuilder.fillColor(Health.getColor(isColorBlind, it.measure, 150))
                googleMap.addCircle(circleBuilder)
            }
        }
    }
}