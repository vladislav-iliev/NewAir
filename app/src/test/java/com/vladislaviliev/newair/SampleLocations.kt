package com.vladislaviliev.newair

import com.vladislaviliev.air.user.location.UserLocation

object SampleLocations {
    private fun generateAlphabeticalLocations(): List<UserLocation> {
        val alphabet = ('A'..'Z').toList()
        val indexesPerLetter = 2
        val locations = mutableListOf<UserLocation>()
        alphabet.forEach { letter ->
            repeat(indexesPerLetter) { indexWithinLetter ->
                locations.add(UserLocation(0, "$letter${indexWithinLetter + 1}", 0.0, 0.0))
            }
        }
        return locations
    }

    val locations = generateAlphabeticalLocations()
}