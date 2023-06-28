package com.vladislaviliev.newair.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.RuntimeData
import com.vladislaviliev.newair.sensor.SensorType
import com.vladislaviliev.newair.sensor.Utils

class Fragment : Fragment() {

    private val appData: RuntimeData by activityViewModels()
    private val homeData: Data by viewModels()

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
        homeData.setLocations(appData.userLocations)
        homeData.setPosition(0)
        isColorBlind =
            PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(getString(R.string.color_blind_switch_key), false)
        carousel = Carousel(requireView(), homeData)
        backgroundView = view.findViewById(R.id.container)
        healthView = view.findViewById(R.id.healthMessage)
        pollutionView = view.findViewById(R.id.pollutionText)
        temperatureView = view.findViewById(R.id.temperatureText)
        humidityView = view.findViewById(R.id.humidityText)
        view.findViewById<View>(R.id.addButton).setOnClickListener {
            NavHostFragment.findNavController(this).navigate(FragmentDirections.actionNavigationHomeToNavigationMap(false))
        }
        view.findViewById<View>(R.id.settingsButton).setOnClickListener {
            NavHostFragment.findNavController(this).navigate(FragmentDirections.actionNavigationHomeToNavigationSettings())
        }
        view.findViewById<View>(R.id.refreshButton).setOnClickListener { appData.download() }
        appData.liveSensors.observe(viewLifecycleOwner) { redrawReadings() }
        homeData.position.observe(viewLifecycleOwner) { carousel.redrawPosition(); redrawReadings() }
    }

    private fun redrawReadings() {
        val sensors = appData.liveSensors.value!!
        val latLng = homeData.getCurrentLatLng(appData.userLocations)
        val pollution = Utils.getReading(latLng, sensors, SensorType.PM10)
        val temp = Utils.getReading(latLng, sensors, SensorType.TEMP)
        val humid = Utils.getReading(latLng, sensors, SensorType.HUMID)
        pollutionView.text = pollution.toString()
        temperatureView.text = temp.toString()
        humidityView.text = humid.toString()
        healthView.text = Utils.getHealthMessage(pollution)
        backgroundView.setBackgroundColor(Utils.getColor(isColorBlind, pollution))
    }
}