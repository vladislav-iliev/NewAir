package com.example.newair.data.sensors.history_data

import java.util.*


/**
 * Stores all history sensor data.
 * @author Vladislav Iliev
 */
class HistoryData {
    /**
     * Returns the past week dates
     * @return the past week dates
     */
    /**
     * The past week, including today
     */
    lateinit var dates: Array<Date>
        private set

    /**
     * Returns the past week PM25 readings
     * @return the past week PM25 readings
     */
    lateinit var pm25: DoubleArray
        private set

    /**
     * Returns the past week PM10 readings
     * @return the past week PM10 readings
     */
    lateinit var pm10: DoubleArray
        private set

    /**
     * Returns the past week O3 readings
     * @return the past week O3 readings
     */
    lateinit var o3: DoubleArray
        private set

    init {
        initializeDatesList()
        initializeLists()
    }

    /**
     * Calculate the previous week's dates
     */
    private fun initializeDatesList() {
        val dates = mutableListOf<Date>()
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        dates.add(dateNullifyTime(calendar.time))
        (1 until 7).forEach { _ ->
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            dates.add(dateNullifyTime(calendar.time))
        }
        this.dates = dates.toTypedArray()
    }

    /**
     * Initialize the pollution storage lists
     */
    private fun initializeLists() {
        pm25 = DoubleArray(dates.size)
        pm10 = DoubleArray(dates.size)
        o3 = DoubleArray(dates.size)

        // Fill with invalid integers (to avoid null exception on potential
        // use of values)
        val invalidInteger = (-1).toDouble()
        pm25.indices.forEach {
            pm25[it] = invalidInteger
            pm10[it] = invalidInteger
            o3[it] = invalidInteger
        }
    }

    companion object {
        /**
         * Set a Date's time to 00:00:00.
         * Makes easier comparisons between weekdays.
         * @param date the new Date
         * @return the new Date with time set to 00:00:00
         */
        @JvmStatic
        fun dateNullifyTime(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar.time
        }
    }
}