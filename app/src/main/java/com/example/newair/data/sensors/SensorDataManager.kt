package com.example.newair.data.sensors

import com.example.newair.data.sensors.Sensor.SensorType
import com.google.android.gms.maps.model.LatLng
import java.util.Collections
import kotlin.math.roundToInt

class SensorDataManager {

    var lastLiveData = listOf<Sensor>()
    var lastHistoryData = listOf<Double>()
    var loading = false

    private fun random() = (0..100).random().toDouble()

    fun loadLiveSensors(): List<Sensor> {
        if (loading) return lastLiveData
        loading = true
        val res = mutableListOf<Sensor>()
        res.add(Sensor(SensorType.PM10, LatLng(54.973918, -1.624631), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.979667, -1.626219), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.982082, -1.616685), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.973908, -1.618864), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.974662, -1.635057), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.971311, -1.611821), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.967006, -1.613870), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.962771, -1.620061), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.957124, -1.649998), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.980470, -1.593832), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.973706, -1.614780), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.981417, -1.610347), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.976402, -1.608904), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.971259, -1.619555), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.970717, -1.622784), random()))
        res.add(Sensor(SensorType.PM10, LatLng(54.979261, -1.613316), random()))


        res.add(Sensor(SensorType.HUMID, LatLng(54.973918, -1.624631), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.979667, -1.626219), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.982082, -1.616685), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.973908, -1.618864), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.974662, -1.635057), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.971311, -1.611821), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.967006, -1.613870), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.962771, -1.620061), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.957124, -1.649998), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.980470, -1.593832), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.976087, -1.610695), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.972089, -1.612046), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.970616, -1.612827), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.973706, -1.614780), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.981417, -1.610347), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.976402, -1.608904), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.971259, -1.619555), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.970717, -1.622784), random()))
        res.add(Sensor(SensorType.HUMID, LatLng(54.979261, -1.613316), random()))

        res.add(Sensor(SensorType.TEMP, LatLng(54.973918, -1.624631), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.979667, -1.626219), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.982082, -1.616685), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.973908, -1.618864), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.974662, -1.635057), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.971311, -1.611821), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.967006, -1.613870), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.962771, -1.620061), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.957124, -1.649998), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.980470, -1.593832), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.976087, -1.610695), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.972089, -1.612046), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.970616, -1.612827), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.973706, -1.614780), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.981417, -1.610347), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.976402, -1.608904), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.971259, -1.619555), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.970717, -1.622784), random()))
        res.add(Sensor(SensorType.TEMP, LatLng(54.979261, -1.613316), random()))
        lastLiveData = res
        loading = false
        return Collections.unmodifiableList(lastLiveData)
    }

    fun loadHistorySensors(): List<Double> {
        if (loading) return lastHistoryData
        loading = true
        val res = mutableListOf<Double>()
        res.add(random())
        res.add(random())
        res.add(random())
        res.add(random())
        res.add(random())
        res.add(random())
        lastHistoryData = res
        loading = false
        return Collections.unmodifiableList(lastHistoryData)
    }

    companion object {
        /**
         * Rounds a floating-point number to its first digit
         * @param value the number
         * @return the rounded to first digit result
         */
        @JvmStatic
        fun roundToFirstDigit(value: Double) = (value * 10).roundToInt().toDouble() / 10
    }
}