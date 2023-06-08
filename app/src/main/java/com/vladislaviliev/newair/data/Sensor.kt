package com.vladislaviliev.newair.data

import com.google.android.gms.maps.model.LatLng

data class Sensor(val type: SensorType, val latLng: LatLng, val measure: Double) : Comparable<Sensor> {
    override fun compareTo(other: Sensor) = measure.compareTo(other.measure)
}