package com.vladislaviliev.newair.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.model.LatLng
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.Vm
import com.vladislaviliev.newair.data.SensorType
import com.vladislaviliev.newair.data.distanceBetween
import com.vladislaviliev.newair.data.getColor
import com.vladislaviliev.newair.data.getHealthMessage

class HomeFragment : Fragment() {

    private val vm: Vm by activityViewModels()

    private var isColorBlind = false
    private lateinit var carousel: Carousel
    private lateinit var backgroundView: View
    private lateinit var pollutionView: TextView
    private lateinit var healthView: TextView
    private lateinit var temperatureView: TextView
    private lateinit var humidityView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isColorBlind =
            PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(getString(R.string.color_blind_switch_key), false)
        carousel = Carousel(this, vm.userLocations)
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
        vm.liveSensors.observe(viewLifecycleOwner) { redrawReadings() }
    }

    internal fun redrawReadings() {
        val latLng = carousel.getCurrentLatLng()
        val pollution = getReading(latLng, SensorType.PM10)
        val temp = getReading(latLng, SensorType.TEMP)
        val humid = getReading(latLng, SensorType.HUMID)
        pollutionView.text = pollution.toString()
        temperatureView.text = temp.toString()
        humidityView.text = humid.toString()
        healthView.text = getHealthMessage(pollution)
        backgroundView.setBackgroundColor(getColor(isColorBlind, pollution))
    }

    private fun getReading(latLng: LatLng?, type: SensorType) = if (latLng == null) getAverage(type) else closestReading(latLng, type)

    private fun getAverage(type: SensorType): Double {
        val sensors = vm.liveSensors.value!!
        return (sensors.filter { it.type == type }.sumOf { it.measure } / sensors.size).toInt().toDouble()
    }

    private fun closestReading(latLng: LatLng, type: SensorType) = vm.liveSensors.value!!
        .filter { it.type == type }.minWith { a, b -> (distanceBetween(latLng, a.latLng) - distanceBetween(latLng, b.latLng)).toInt() }
        .measure
}