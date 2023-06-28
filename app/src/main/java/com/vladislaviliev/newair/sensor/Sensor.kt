package com.vladislaviliev.newair.sensor

import com.google.android.gms.maps.model.LatLng

data class Sensor(val type: SensorType, val latLng: LatLng, val measure: Double)