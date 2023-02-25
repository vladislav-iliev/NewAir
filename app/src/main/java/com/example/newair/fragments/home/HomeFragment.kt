package com.example.newair.fragments.home

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.example.newair.R
import com.example.newair.data.SensorViewModel
import com.example.newair.data.sensors.Sensor
import com.example.newair.data.user_locations.UserLocation
import com.google.android.gms.maps.model.LatLng

class HomeFragment : Fragment(), OnClickListener {

    private val viewModel: SensorViewModel by activityViewModels()
    private lateinit var carousel: HomeCarousel

    private lateinit var rootView: View
    private lateinit var backgroundView: View
    private lateinit var pollutionView: TextView
    private lateinit var healthView: TextView
    private lateinit var temperatureView: TextView
    private lateinit var humidityView: TextView
    private var isColorBlind = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carousel = HomeCarousel(this).apply { addLocations(viewModel.uiState.value!!.userLocations) }
        rootView = requireView()
        backgroundView = rootView.findViewById(R.id.container)
        healthView = rootView.findViewById(R.id.healthMessage)
        pollutionView = rootView.findViewById(R.id.pollutionText)
        temperatureView = rootView.findViewById(R.id.temperatureText)
        humidityView = rootView.findViewById(R.id.humidityText)
        isColorBlind = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getBoolean(getString(R.string.color_blind_switch_key), false)

        view.findViewById<View>(R.id.addButton).setOnClickListener(this)
        view.findViewById<View>(R.id.refreshButton).setOnClickListener(this)
        view.findViewById<View>(R.id.settingsButton).setOnClickListener(this)

        viewModel.uiState.observe(viewLifecycleOwner) { updateScreen() }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addButton -> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMap(false))
            R.id.refreshButton -> {
                viewModel.downloadData()
            }
            R.id.settingsButton -> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.actionNavigationHomeToNavigationSettings())
        }
    }

    fun updateScreen() {
        val uiState = viewModel.uiState.value!!

        val pos = carousel.currentPos
        val currentLocation: UserLocation? = if (pos == 0) null else uiState.userLocations[pos - 1]

        val sensors = uiState.liveData
        val newPollution =
            if (currentLocation == null) getAverage(sensors, Sensor.SensorType.PM10)
            else findClosestUserLocationSensor(sensors, currentLocation, Sensor.SensorType.PM10).measure
        val newTemperature =
            if (currentLocation == null) getAverage(sensors, Sensor.SensorType.TEMP)
            else findClosestUserLocationSensor(sensors, currentLocation, Sensor.SensorType.TEMP).measure
        val newHumidity =
            if (currentLocation == null) getAverage(sensors, Sensor.SensorType.HUMID)
            else findClosestUserLocationSensor(sensors, currentLocation, Sensor.SensorType.HUMID).measure

        pollutionView.text = (newPollution.toString())
        temperatureView.text = newTemperature.toString()
        humidityView.text = newHumidity.toString()
        setColorAndHealthMessage(newPollution)
    }

    private fun setColorAndHealthMessage(newPollution: Double) {
        if (newPollution < 0) {
            backgroundView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
            healthView.text = getString(R.string.data_not_avaiable)
            return
        }

        val colorStartValues = resources.getIntArray(R.array.color_dividers_int)
        var pos = colorStartValues.indexOfFirst { newPollution <= it }
        if (pos < 0) pos = colorStartValues.lastIndex

        backgroundView.setBackgroundColor(
            if (isColorBlind) resources.getIntArray(R.array.colors_colorblind)[pos]
            else resources.getIntArray(R.array.colors)[pos]
        )
        healthView.text = resources.getStringArray(R.array.health_messages)[pos]
    }

    private fun getAverage(sensors: List<Sensor>, type: Sensor.SensorType) =
        (sensors.filter { it.type == type }.sumOf { it.measure } / sensors.size).toInt().toDouble()

    private fun findClosestUserLocationSensor(
        sensors: List<Sensor>,
        userLocation: UserLocation,
        type: Sensor.SensorType
    ): Sensor {
        val userLoc = userLocation.latLng
        return sensors.filter { it.type == type }
            .minWith { a, b -> (distanceBetween(userLoc, a.latLng) - distanceBetween(userLoc, b.latLng)).toInt() }
    }

    private fun distanceBetween(a: LatLng, b: LatLng) = Location("a").apply {
        latitude = a.latitude
        longitude = a.longitude
    }.distanceTo(Location("b").apply {
        latitude = b.latitude
        longitude = b.longitude
    })
}