package com.vladislaviliev.newair.userlocation

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.vladislaviliev.newair.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Storage(private val context: Context) {

    private val file: File = File(context.filesDir, context.getString(R.string.user_locations_file))
    private val delimiter = '|'
    private val detailsDelimiter = '~'

    init {
        checkUserLocationsFile()
    }

    private fun checkUserLocationsFile() {
        try {
            if (file.createNewFile()) {
                Log.e(
                    context.getString(R.string.user_locations_file_tag),
                    context.getString(R.string.user_locations_file_already_exists_message)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun read(): Collection<UserLocation> {
        val readLocations = mutableListOf<UserLocation>()
        try {
            ObjectInputStream(FileInputStream(file)).use { stream ->
                val incStr = stream.readObject() as String
                if (incStr.isEmpty()) return listOf()
                for (locString in incStr.split(delimiter)) {
                    if (locString.isEmpty()) continue
                    val loc = locString.split(detailsDelimiter)
                    val name = loc[0]
                    val newLatLng = LatLng(loc[1].toDouble(), loc[2].toDouble())
                    readLocations.add(UserLocation(name, newLatLng))
                }
            }
        } catch (e: IOException) {
            Log.e(context.getString(R.string.user_locations_file_tag), e.toString())
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return readLocations
    }

    fun save(userLocations: Iterable<UserLocation>) {
        val toSave = StringBuilder()
        for (location in userLocations) {
            toSave.append(location.name)
                .append(detailsDelimiter).append(location.latLng.latitude.toString())
                .append(detailsDelimiter).append(location.latLng.longitude.toString())
                .append(delimiter)
        }
        try {
            ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(toSave.toString()) }
        } catch (e: IOException) {
            Log.e(context.getString(R.string.user_locations_file_tag), e.toString())
        }
    }
}