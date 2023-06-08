package com.vladislaviliev.newair.fragments.home

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.model.LatLng
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.Vm
import com.vladislaviliev.newair.data.Sensor
import com.vladislaviliev.newair.data.SensorType
import com.vladislaviliev.newair.data.UserLocation

class HomeFragment : Fragment() {

    internal val vm: Vm by activityViewModels()

    private lateinit var carousel: HomeCarousel
    private lateinit var backgroundView: View
    private lateinit var pollutionView: TextView
    private lateinit var healthView: TextView
    private lateinit var temperatureView: TextView
    private lateinit var humidityView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carousel = HomeCarousel(this)
        backgroundView = view.findViewById(R.id.container)
        healthView = view.findViewById(R.id.healthMessage)
        pollutionView = view.findViewById(R.id.pollutionText)
        temperatureView = view.findViewById(R.id.temperatureText)
        humidityView = view.findViewById(R.id.humidityText)
        view.findViewById<View>(R.id.addButton).setOnClickListener {
            NavHostFragment.findNavController(this).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMap(false))
        }
        view.findViewById<View>(R.id.settingsButton).setOnClickListener {
            NavHostFragment.findNavController(this).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationSettings())
        }
        view.findViewById<View>(R.id.refreshButton).setOnClickListener { vm.downloadData() }
        vm.liveSensors.observe(viewLifecycleOwner) { updateScreen() }
    }

    internal fun updateScreen() {
        val loc: UserLocation? = if (carousel.position == 0) null else vm.userLocations[carousel.position - 1]
        val pollution = if (loc == null) getAverage(SensorType.PM10) else closestSensor(loc.latLng, SensorType.PM10).measure
        val temp = if (loc == null) getAverage(SensorType.TEMP) else closestSensor(loc.latLng, SensorType.TEMP).measure
        val humid = if (loc == null) getAverage(SensorType.HUMID) else closestSensor(loc.latLng, SensorType.HUMID).measure
        carousel.checkArrowsVisibility(carousel.position)
        pollutionView.text = pollution.toString()
        temperatureView.text = temp.toString()
        humidityView.text = humid.toString()
        healthView.text = getHealthMessage(pollution)
        backgroundView.setBackgroundColor(getColor(pollution))
    }

    private fun getAverage(type: SensorType): Double {
        val sensors = vm.liveSensors.value!!
        return (sensors.filter { it.type == type }.sumOf { it.measure } / sensors.size).toInt().toDouble()
    }

    private fun closestSensor(latLng: LatLng, type: SensorType): Sensor {
        fun distanceBetween(a: LatLng, b: LatLng) = Location("a").apply { latitude = a.latitude; longitude = a.longitude }
            .distanceTo(Location("b").apply { latitude = b.latitude; longitude = b.longitude })
        return vm.liveSensors.value!!
            .filter { it.type == type }
            .minWith { a, b -> (distanceBetween(latLng, a.latLng) - distanceBetween(latLng, b.latLng)).toInt() }
    }

    private fun getThresholdIndex(pollution: Double): Int {
        val thresholds = resources.getIntArray(R.array.color_dividers_int)
        var idx = thresholds.indexOfFirst { pollution <= it }
        if (idx < 0) idx = thresholds.lastIndex
        return idx
    }

    private fun getHealthMessage(pollution: Double) = resources.getStringArray(R.array.health_messages)[getThresholdIndex(pollution)]

    @ColorInt
    private fun getColor(pollution: Double): Int {
        val colorBlind =
            PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(getString(R.string.color_blind_switch_key), false)
        val colors = resources.getIntArray(if (colorBlind) R.array.colors_colorblind else R.array.colors)
        return colors[getThresholdIndex(pollution)]
    }
}