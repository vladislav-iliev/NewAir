package com.example.newair.data.sensors.live_data

import com.example.newair.data.sensors.Sensor
import com.example.newair.data.sensors.Sensor.SensorType
import java.util.*

/**
 * Stores all Live sensor data
 * @author Vladislav Iliev
 */
internal class LiveSensorLists {
    private val pm10Sensors = mutableListOf<Sensor>()
    private val tempSensors = mutableListOf<Sensor>()
    private val humidSensors = mutableListOf<Sensor>()

    private fun getInternalList(type: SensorType) = when (type) {
        SensorType.PM10 -> pm10Sensors
        SensorType.TEMP -> tempSensors
        SensorType.HUMID -> humidSensors
    }

    fun getList(type: SensorType): List<Sensor> = Collections.unmodifiableList(getInternalList(type))

    fun addSensor(sensor: Sensor) = getInternalList(sensor.type).add(sensor)

    fun sortSensorList(type: SensorType) = getInternalList(type).sort()

    fun approxOfList(sensorType: SensorType): Double {
        val listToApproximate = getList(sensorType)
        if (listToApproximate.isEmpty()) return (-1).toDouble()
        var approx = 0.0
        listToApproximate.forEach { approx += it.measure }
        return approx / listToApproximate.size
    }

    fun clearAllLists() {
        pm10Sensors.clear()
        tempSensors.clear()
        humidSensors.clear()
    }
}