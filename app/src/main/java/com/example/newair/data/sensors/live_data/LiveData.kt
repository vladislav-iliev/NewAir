package com.example.newair.data.sensors.live_data

import com.example.newair.data.sensors.Sensor
import com.example.newair.data.sensors.Sensor.SensorType
import com.example.newair.data.sensors.SensorDataManager
import com.example.newair.data.user_locations.UserLocation
import com.google.android.gms.maps.model.LatLng
import org.apache.lucene.util.SloppyMath

class LiveData(private val userLocations: List<UserLocation>) {

    private val liveSensorLists = LiveSensorLists()
    val livePm10Sensors: List<Sensor> get() = liveSensorLists.getList(SensorType.PM10)
    val pollutionLevels: DoubleArray
    val temperatureLevels: DoubleArray
    val humidityLevels: DoubleArray


    init {
        val allLocationsNumber = 1 + userLocations.size // + 1 because of City
        pollutionLevels = DoubleArray(allLocationsNumber)
        temperatureLevels = DoubleArray(allLocationsNumber)
        humidityLevels = DoubleArray(allLocationsNumber)

        // Fill all lists with a negative number (avoid null pointers when
        // using data)
        val invalidInteger = (-1).toDouble()
        for (i in 0 until allLocationsNumber) {
            pollutionLevels[i] = invalidInteger
            temperatureLevels[i] = invalidInteger
            humidityLevels[i] = invalidInteger
        }
    }


    /**
     * Updates all displayed live sensor data
     */
    fun updateData() {
        pollutionLevels[0] = SensorDataManager.roundToFirstDigit(liveSensorLists.approxOfList(SensorType.PM10))
        temperatureLevels[0] = SensorDataManager.roundToFirstDigit(liveSensorLists.approxOfList(SensorType.TEMP))
        humidityLevels[0] = SensorDataManager.roundToFirstDigit(liveSensorLists.approxOfList(SensorType.HUMID))
        updateUserLocations()
    }

    /**
     * Calculate measurements for user locations
     */
    private fun updateUserLocations() {
        val invalidInteger = (-1).toDouble()
        userLocations.indices.forEach {
            val closestPollutionSensor = findClosestUserLocationSensor(userLocations[it], SensorType.PM10)
            val closestTemperatureSensor = findClosestUserLocationSensor(userLocations[it], SensorType.TEMP)
            val closestHumiditySensor = findClosestUserLocationSensor(userLocations[it], SensorType.HUMID)

            // User locations' positions in the Home carousel is pushed right
            // by the default locations (City), add the difference
            // to get the actual user location position
            val carouselPosition = 1 + it
            // If the closest sensor is not available
            // (i.e. no sensors are found), fill with an invalid integer
            pollutionLevels[carouselPosition] =
                if (closestPollutionSensor == null) invalidInteger
                else SensorDataManager.roundToFirstDigit(closestPollutionSensor.measure)
            temperatureLevels[carouselPosition] =
                if (closestTemperatureSensor == null) invalidInteger
                else SensorDataManager.roundToFirstDigit(closestTemperatureSensor.measure)
            humidityLevels[carouselPosition] =
                if (closestHumiditySensor == null) invalidInteger
                else SensorDataManager.roundToFirstDigit(closestHumiditySensor.measure)
        }
    }

    fun addSensor(sensor: Sensor) {
        liveSensorLists.addSensor(sensor)
    }

    fun clearAllSensorLists() {
        liveSensorLists.clearAllLists()
    }

    /**
     * Might need to sort a list by measurements (e.g. Map screen - important
     * to place more polluted circles on top of lower polluted).
     * @param sensorType the sensor list type
     */
    fun sortSensorList(sensorType: SensorType) {
        liveSensorLists.sortSensorList(sensorType)
    }

    /**
     * Find the closest sensor to a custom user location by pollutant type
     * @param userLocation the user location
     * @param type   the pollutant type
     * @return the closest sensor
     */
    private fun findClosestUserLocationSensor(userLocation: UserLocation, type: SensorType): Sensor? {
        val userLoc = userLocation.latLng
        return liveSensorLists.getList(type).minWithOrNull {
                a, b -> (distanceBetween(userLoc, a.latLng) - distanceBetween(userLoc, b.latLng)).toInt()
        }
    }

    private fun distanceBetween(a: LatLng, b: LatLng) =
        SloppyMath.haversinMeters(a.latitude, a.longitude, b.latitude, b.longitude)
}