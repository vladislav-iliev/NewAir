package com.vladislaviliev.newair.data

import com.google.android.gms.maps.model.LatLng

class Sensor(val type: SensorType, val latLng: LatLng, val measure: Double) : Comparable<Sensor> {
    enum class SensorType { PM10, TEMP, HUMID }
    override fun compareTo(other: Sensor) = measure.compareTo(other.measure)
}