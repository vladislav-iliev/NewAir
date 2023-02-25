package com.example.newair.data.sensors

import com.example.newair.data.sensors.Sensor.SensorType
import com.google.android.gms.maps.model.LatLng

internal class Downloader {
    private fun random() = (0..99).random().toDouble()

    fun newLiveSensors() = listOf(
        Sensor(SensorType.PM10, LatLng(54.973918, -1.624631), random()),
        Sensor(SensorType.PM10, LatLng(54.979667, -1.626219), random()),
        Sensor(SensorType.PM10, LatLng(54.982082, -1.616685), random()),
        Sensor(SensorType.PM10, LatLng(54.973908, -1.618864), random()),
        Sensor(SensorType.PM10, LatLng(54.974662, -1.635057), random()),
        Sensor(SensorType.PM10, LatLng(54.971311, -1.611821), random()),
        Sensor(SensorType.PM10, LatLng(54.967006, -1.613870), random()),
        Sensor(SensorType.PM10, LatLng(54.962771, -1.620061), random()),
        Sensor(SensorType.PM10, LatLng(54.973706, -1.614780), random()),
        Sensor(SensorType.PM10, LatLng(54.981417, -1.610347), random()),
        Sensor(SensorType.PM10, LatLng(54.976402, -1.608904), random()),
        Sensor(SensorType.PM10, LatLng(54.971259, -1.619555), random()),
        Sensor(SensorType.PM10, LatLng(54.970717, -1.622784), random()),
        Sensor(SensorType.PM10, LatLng(54.979261, -1.613316), random()),

        Sensor(SensorType.HUMID, LatLng(54.973918, -1.624631), random()),
        Sensor(SensorType.HUMID, LatLng(54.979667, -1.626219), random()),
        Sensor(SensorType.HUMID, LatLng(54.982082, -1.616685), random()),
        Sensor(SensorType.HUMID, LatLng(54.973908, -1.618864), random()),
        Sensor(SensorType.HUMID, LatLng(54.974662, -1.635057), random()),
        Sensor(SensorType.HUMID, LatLng(54.971311, -1.611821), random()),

        Sensor(SensorType.TEMP, LatLng(54.973918, -1.624631), random()),
        Sensor(SensorType.TEMP, LatLng(54.979667, -1.626219), random()),
        Sensor(SensorType.TEMP, LatLng(54.982082, -1.616685), random()),
        Sensor(SensorType.TEMP, LatLng(54.973908, -1.618864), random()),
        Sensor(SensorType.TEMP, LatLng(54.974662, -1.635057), random()),
    )

    fun newHistorySensors() = listOf(random(), random(), random(), random(), random(), random(), random())
}