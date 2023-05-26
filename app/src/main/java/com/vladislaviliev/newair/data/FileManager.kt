package com.vladislaviliev.newair.data

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

class FileManager(private val context: Context) {

    private val locationsFile: File = File(context.filesDir, context.getString(R.string.user_locations_file))
    private val historyDataFile: File = File(context.filesDir, context.getString(R.string.history_data_file))

    init {
        checkUserLocationsFile()
    }

    private fun checkUserLocationsFile() {
        try {
            if (locationsFile.createNewFile()) {
                Log.e(
                    context.getString(R.string.user_locations_file_tag),
                    context.getString(R.string.user_locations_file_already_exists_message)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Read the stored user locations
     */
    fun readUserLocationsFile(): Collection<UserLocation> {
        try {
            ObjectInputStream(FileInputStream(locationsFile)).use { inStr ->
                val res: MutableList<UserLocation> = ArrayList()
                val incStr = inStr.readObject() as String
                if (incStr.isEmpty()) return listOf()
                val inc = incStr.split('|')
                for (i in inc.indices) {
                    if (inc[i].isEmpty()) continue
                    val loc = inc[i].split('~')
                    val lat = loc[1].toDouble()
                    val long = loc[2].toDouble()
                    val newName = loc[0]
                    val newLatLng = LatLng(lat, long)
                    res.add(UserLocation(newName, newLatLng))
                }
                return res
            }
        } catch (e: IOException) {
            Log.e(context.getString(R.string.user_locations_file_tag), e.toString())
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return listOf()
    }

    /**
     * Save the current user locations to the file
     */
    fun saveUserLocations(userLocations: Iterable<UserLocation>) {
        try {
            ObjectOutputStream(FileOutputStream(locationsFile)).use { os ->
                val toSave = StringBuilder()
                for (location in userLocations) {
                    toSave.append(location.name).append('~')
                        .append(location.latLng.latitude.toString())
                        .append('~').append(location.latLng.longitude.toString()).append('|')
                }
                os.writeObject(toSave.toString())
            }
        } catch (e: IOException) {
            Log.e(context.getString(R.string.user_locations_file_tag), e.toString())
        }
    }

    /**
     * If a history data file doesn't exist, create one
     */
    private fun checkHistoryFile() {
        try {
            if (historyDataFile.createNewFile()) {
                Log.e(
                    context.getString(R.string.history_data_file_tag),
                    context.getString(R.string.history_data_file_already_exists_message)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Read the history data file.
     */
    private fun readHistoryDataFile(): List<Double> {
        try {
            ObjectInputStream(FileInputStream(historyDataFile)).use { inStr ->
                val savedHistoryDataArray = inStr.readObject() as? Array<*>
                val res: MutableList<Double> = ArrayList()
                for (i in 0..6) res.add(savedHistoryDataArray?.get(i) as Double)
                return res
            }
        } catch (e: IOException) {
            Log.e(
                context.getString(R.string.history_data_file_tag),
                context.getString(R.string.history_data_file_empty)
            )
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return listOf()
    }

    /**
     * Save the current history data to the file
     */
    private fun saveHistoryData(historyData: List<Double>) {
        if (!historyDataSafeToSave(historyData)) return
        try {
            ObjectOutputStream(FileOutputStream(historyDataFile)).use { os ->
                val toSave = arrayOfNulls<String>(6)
                for (i in 0..5) toSave[i] = historyData[i].toString()
                os.writeObject(toSave)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * If one of the days has an invalid reading on any polluter (-1), the data
     * should not be saved
     *
     * @return Whether the data is safe to save
     */
    private fun historyDataSafeToSave(historyData: List<Double>) = historyData.contains((-1).toDouble())
}