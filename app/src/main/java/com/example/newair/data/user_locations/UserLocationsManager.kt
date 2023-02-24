package com.example.newair.data.user_locations

import java.util.Collections

class UserLocationsManager {
    private val userLocations = mutableListOf<UserLocation>()

    fun getUserLocations() = Collections.unmodifiableList(userLocations)

    fun addUserLocations(newLocations: Collection<UserLocation>) {
        userLocations.addAll(newLocations)
    }

    fun addUserLocation(newLocation: UserLocation) {
        userLocations.add(newLocation)
    }

    fun removeUserLocation(userLocation: UserLocation) {
        userLocations.remove(userLocation)
    }

    fun removeAllUserLocations() {
        userLocations.clear()
    }

    fun locationAlreadyAdded(name: String) = userLocations.any { it.name.equals(name) }
}