package com.vladislaviliev.newair.readings.calculations

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import kotlin.collections.windowed

object Health {
    private val thresholds = listOf(0, 6, 17, 34, 51, 59, 67, 76, 84, 92, 101)
    private val healthMessages = listOf(
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

    private val backgroundColors = listOf(
        Color(0xFF47BBF0),
        Color(0xFF5BC78E),
        Color(0xFF57BB67),
        Color(0xFF44986A),
        Color(0xFFF39457),
        Color(0xFFF17C32),
        Color(0xFFEA642F),
        Color(0xFFED4639),
        Color(0xFFD63F3E),
        Color(0xFFCA3B3A),
        Color(0xFF8E3276),
    )

    private val backgroundColorsColorBlind = listOf(
        Color(0xFF45A7AF),
        Color(0xFFC04CBF),
        Color(0xFF33A2F7),
        Color(0xFF0E5DC5),
        Color(0xFFBF913D),
        Color(0xFF5FBF42),
        Color(0xFF918F19),
        Color(0xFFBA3523),
        Color(0xFF96311A),
        Color(0xFF77212C),
        Color(0xFF33723D),
    )

    private fun getThresholdIndex(pollution: Double): Int {
        var idx = thresholds.indexOfFirst { pollution <= it }
        if (idx < 0) idx = thresholds.lastIndex
        return idx
    }

    private fun getColors(isColorBlind: Boolean) =
        if (isColorBlind) backgroundColorsColorBlind else backgroundColors

    private fun getColor(isColorBlind: Boolean, index: Int) =
        getColors(isColorBlind)[index]

    fun getColor(isColorBlind: Boolean, pollution: Double) =
        getColors(isColorBlind)[getThresholdIndex(pollution)]

    fun getHealthMessage(pollution: Double) = healthMessages[getThresholdIndex(pollution)]

    @Composable
    fun checkUnspecifiedBackgroundColor(color: Color) =
        color.takeOrElse(MaterialTheme.colorScheme::surface)

    @Composable
    fun checkUnspecifiedContentColor(color: Color) =
        color.takeOrElse(MaterialTheme.colorScheme::onSurface)

    /** ["0 - 6" -> Blue], ["6 - 17" -> Green] ... */
    fun getThresholdsToColors(isColorBlind: Boolean) = thresholds
        .windowed(size = 2, partialWindows = true) {
            if (it.size > 1) "${it[0]} - ${it[1]}" else "${it[0]} - "
        }.mapIndexed { idx, str -> str to getColor(isColorBlind, idx) }
}