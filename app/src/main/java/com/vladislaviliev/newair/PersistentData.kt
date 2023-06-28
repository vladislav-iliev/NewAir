package com.vladislaviliev.newair

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class PersistentData(root: File) {

    private val file: File = File(root, "storage.ser")
    private val tag = "PERSISTENT_STORAGE"
    private val delimiter = '|'
    private val detailsDelimiter = '~'

    init {
        checkFile()
    }

    private fun checkFile() {
        try {
            if (file.createNewFile()) Log.e(tag, "FILE ALREADY EXISTS")
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
            Log.e(tag, e.toString())
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
            Log.e(tag, e.toString())
        }
    }
}