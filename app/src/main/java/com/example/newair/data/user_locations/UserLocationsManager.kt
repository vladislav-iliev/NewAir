package com.example.newair.data.user_locations

import java.util.Collections

class UserLocationsManager {
    private val userLocations = mutableListOf<UserLocation>()

    fun getUserLocations(): List<UserLocation> = Collections.unmodifiableList(userLocations)

    fun addUserLocations(newLocations: Collection<UserLocation>) {
        userLocations.addAll(newLocations)
    }

    fun removeUserLocation(name: String) {
        userLocations.remove(userLocations.first { it.name == name })
    }

    fun removeAllUserLocations() {
        userLocations.clear()
    }

    fun locationAlreadyAdded(name: String) = userLocations.any { it.name == name }
}