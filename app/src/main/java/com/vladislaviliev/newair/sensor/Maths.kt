package com.vladislaviliev.newair.sensor

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object Maths {
    private fun distanceBetween(a: LatLng, b: LatLng) = Location("a").apply { latitude = a.latitude; longitude = a.longitude }
        .distanceTo(Location("b").apply { latitude = b.latitude; longitude = b.longitude })

    private fun getAverage(sensors: Collection<Sensor>) = (sensors.sumOf { it.measure } / sensors.size).toInt().toDouble()

    private fun closestReading(latLng: LatLng, sensors: Iterable<Sensor>) =
        sensors.minWith { a, b -> (distanceBetween(latLng, a.latLng) - distanceBetween(latLng, b.latLng)).toInt() }.measure

    fun getReading(latLng: LatLng?, sensors: List<Sensor>, type: SensorType): Double {
        val filteredSensors = sensors.filter { it.type == type }
        return if (latLng == null) getAverage(filteredSensors) else closestReading(latLng, filteredSensors)
    }
}