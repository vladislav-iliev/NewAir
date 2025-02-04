package com.vladislaviliev.newair

import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.screens.home.screen.state.State
import com.vladislaviliev.newair.screens.home.screen.uiComponents.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getStr(id: Int) = context.getString(id)

    @Test
    fun top_app_bar_always_displayed() {
        composeTestRule.startHome(Loading.value)
        topAppBarDisplayed()
    }

    @Test
    fun location_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        locationDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(location = "Test"))
        locationDisplayed(true)
    }

    @Test
    fun pollution_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        pollutionDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(pollution = "Test"))
        pollutionDisplayed(true)
    }

    @Test
    fun message_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        loadingMessageDisplayed(true)
        composeTestRule.startHome(Loading.value.copy(message = R.string.refresh))
        loadingMessageDisplayed(false)
    }

    @Test
    fun error_message_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        loadingMessageDisplayed(true)
        errorMessageDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(errorMessage = "Test"))
        messageDisplayed(true)
        errorMessageDisplayed(true)
    }

    @Test
    fun temperature_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        temperatureDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(temperature = "Test"))
        temperatureDisplayed(true)
    }

    @Test
    fun humidity_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        humidityDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(humidity = "Test"))
        humidityDisplayed(true)
    }

    @Test
    fun timestamp_displayed_reflects_state() {
        composeTestRule.startHome(Loading.value)
        timestampDisplayed(false)
        composeTestRule.startHome(Loading.value.copy(timestamp = "Test"))
        timestampDisplayed(true)
    }

    @Test
    fun startup_tate_displays_only_loading_message() {
        composeTestRule.startHome(Loading.value)
        topAppBarDisplayed()
        locationDisplayed(false)
        pollutionDisplayed(false)
        loadingMessageDisplayed(true)
        temperatureDisplayed(false)
        humidityDisplayed(false)
    }

    private fun SemanticsNodeInteraction.isDisplayed(displayed: Boolean) =
        if (displayed) assertIsDisplayed() else assertDoesNotExist()

    private fun assertIsDisplayedByContentDescription(
        displayed: Boolean, @StringRes vararg descriptions: Int
    ) {
        for (@StringRes res in descriptions)
            composeTestRule.onNodeWithContentDescription(getStr(res), true).isDisplayed(displayed)
    }

    private fun topAppBarDisplayed() =
        assertIsDisplayedByContentDescription(true, R.string.add_new_location, R.string.refresh)


    private fun locationDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.select_location)


    private fun pollutionDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.air_pollution)


    private fun messageDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.message)


    private fun errorMessageDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.error_message)


    private fun loadingMessageDisplayed(yes: Boolean) {
        messageDisplayed(yes)
        errorMessageDisplayed(false)
        composeTestRule.onNodeWithText(context.getString(R.string.loading)).isDisplayed(yes)
        composeTestRule
            .onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo))
            .isDisplayed(yes)
    }

    private fun temperatureDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.temperature)


    private fun humidityDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.humidity)

    private fun timestampDisplayed(yes: Boolean) =
        assertIsDisplayedByContentDescription(yes, R.string.timestamp)

    private fun ComposeContentTestRule.startHome(state: State) = setContent {
        Screen({}, {}, {}, state)
    }
}