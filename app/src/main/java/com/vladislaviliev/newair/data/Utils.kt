package com.vladislaviliev.newair.data

import android.graphics.Color
import android.location.Location
import androidx.annotation.ColorInt
import com.google.android.gms.maps.model.LatLng

object Utils {
    private val thresholds = arrayOf(0, 6, 17, 34, 51, 59, 67, 76, 84, 92, 101)
    private val healthMessages = arrayOf(
        "Enjoy your usual\noutdoor activities",
        "Enjoy your usual\noutdoor activities",
        "Enjoy your usual\noutdoor activities",
        "Enjoy your usual\noutdoor activities",
        "Possible experience\nof discomfort",
        "Possible experience\nof discomfort",
        "Possible experience\nof discomfort",
        "If uncomfortable\nconsider reducing activity",
        "If uncomfortable\nconsider reducing activity",
        "If uncomfortable\nconsider reducing activity",
        "Reduce physical\nexertion"
    )

    @ColorInt
    private val colors = arrayOf(
        Color.parseColor("#47BBF0"),
        Color.parseColor("#5BC78E"),
        Color.parseColor("#57BB67"),
        Color.parseColor("#44986A"),
        Color.parseColor("#F39457"),
        Color.parseColor("#F17C32"),
        Color.parseColor("#EA642F"),
        Color.parseColor("#ED4639"),
        Color.parseColor("#D63F3E"),
        Color.parseColor("#CA3B3A"),
        Color.parseColor("#8E3276"),
    )

    @ColorInt
    private val colorsColorBlind = arrayOf(
        Color.parseColor("#45A7AF"),
        Color.parseColor("#C04CBF"),
        Color.parseColor("#33A2F7"),
        Color.parseColor("#0E5DC5"),
        Color.parseColor("#BF913D"),
        Color.parseColor("#5FBF42"),
        Color.parseColor("#918F19"),
        Color.parseColor("#BA3523"),
        Color.parseColor("#96311A"),
        Color.parseColor("#77212C"),
        Color.parseColor("#33723D"),
    )

    private fun getThresholdIndex(pollution: Double): Int {
        var idx = thresholds.indexOfFirst { pollution <= it }
        if (idx < 0) idx = thresholds.lastIndex
        return idx
    }

    @ColorInt
    private fun getColors(isColorBlind: Boolean) = if (isColorBlind) colorsColorBlind else colors

    @ColorInt
    fun getColor(isColorBlind: Boolean, index: Int) = getColors(isColorBlind)[index]

    @ColorInt
    fun getColor(isColorBlind: Boolean, pollution: Double) = getColors(isColorBlind)[getThresholdIndex(pollution)]

    @ColorInt
    fun getColor(isColorBlind: Boolean, pollution: Double, alpha: Int): Int {
        val color = getColor(isColorBlind, pollution)
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

    fun getHealthMessage(pollution: Double) = healthMessages[getThresholdIndex(pollution)]

    fun distanceBetween(a: LatLng, b: LatLng) = Location("a").apply { latitude = a.latitude; longitude = a.longitude }
        .distanceTo(Location("b").apply { latitude = b.latitude; longitude = b.longitude })
}