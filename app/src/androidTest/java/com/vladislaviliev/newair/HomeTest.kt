package com.vladislaviliev.newair

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.newair.CommonFunctions.getString
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

    private val location
        get() =
            composeTestRule.onNodeWithContentDescription(
                getString(R.string.click_to_change_location), true
            )

    private val pollution
        get() = composeTestRule.onNodeWithContentDescription(
            getString(R.string.air_pollution), true
        )

    private val message
        get() = composeTestRule.onNodeWithContentDescription(
            getString(R.string.message), true
        )

    private val messageLoading get() = composeTestRule.onNodeWithText(getString(R.string.loading))

    private val errorMessage
        get() = composeTestRule.onNodeWithContentDescription(
            getString(R.string.error_message), true
        )

    private val progressIndicator
        get() = composeTestRule.onNode(
            SemanticsMatcher.keyIsDefined(
                SemanticsProperties.ProgressBarRangeInfo
            )
        )

    private val humidity
        get() = composeTestRule.onNodeWithContentDescription(
            getString(R.string.humidity), true
        )

    private val temperature =
        composeTestRule.onNodeWithContentDescription(getString(R.string.temperature), true)

    private val timestamp
        get() = composeTestRule.onNodeWithContentDescription(
            getString(R.string.timestamp), true
        )

    @Test
    fun top_app_bar_always_displayed() {
        composeTestRule.startHome(Loading.value)
        topAppBarDisplayed()
    }

    @Test
    fun location_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        location.assertDoesNotExist()
    }

    @Test
    fun location_displayed_when_state_has_it() {
        composeTestRule.startHome(Loading.value.copy(location = "Test"))
        location.assertIsDisplayed()
    }

    @Test
    fun pollution_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        pollution.assertDoesNotExist()
    }

    @Test
    fun pollution_displayed_when_state_has_it() {
        composeTestRule.startHome(Loading.value.copy(pollution = "Test"))
        pollution.assertIsDisplayed()
    }

    @Test
    fun message_loading_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        loadingMessageDisplayed(true)
    }

    @Test
    fun message_loading_not_displayed_when_another_message_shown() {
        composeTestRule.startHome(Loading.value.copy(message = R.string.refresh))
        loadingMessageDisplayed(false)
    }

    @Test
    fun error_message_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        loadingMessageDisplayed(true)
        errorMessage.assertDoesNotExist()
    }

    @Test
    fun error_message_displayed_when_state_has_it() {
        composeTestRule.startHome(Loading.value.copy(errorMessage = "Test"))
        message.assertIsDisplayed()
        errorMessage.assertIsDisplayed()
    }

    @Test
    fun temperature_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        temperature.assertDoesNotExist()
    }

    @Test
    fun humidity_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        humidity.assertDoesNotExist()
    }

    @Test
    fun humidity_and_temp_not_shown_when_only_temp() {
        composeTestRule.startHome(Loading.value.copy(temperature = "Test"))
        humidity.assertDoesNotExist()
        temperature.assertDoesNotExist()
    }

    @Test
    fun humidity_and_temp_not_shown_when_only_humidity() {
        composeTestRule.startHome(Loading.value.copy(humidity = "Test"))
        humidity.assertDoesNotExist()
        temperature.assertDoesNotExist()
    }

    @Test
    fun humidity_and_temp_shown_when_both_available() {
        composeTestRule.startHome(Loading.value.copy(temperature = "Test", humidity = "Test"))
        temperature.assertIsDisplayed()
        humidity.assertIsDisplayed()
    }

    @Test
    fun timestamp_not_displayed_when_loading() {
        composeTestRule.startHome(Loading.value)
        timestamp.assertDoesNotExist()
    }

    @Test
    fun timestamp_displayed_when_state_has_it() {
        val testVal = "TestTimestamp"
        composeTestRule.startHome(Loading.value.copy(timestamp = testVal))
        timestamp.assertTextContains(testVal)
    }


    @Test
    fun startup_tate_displays_only_loading_message() {
        composeTestRule.startHome(Loading.value)
        topAppBarDisplayed()
        location.assertDoesNotExist()
        pollution.assertDoesNotExist()
        messageLoading.assertIsDisplayed()
        temperature.assertDoesNotExist()
        humidity.assertDoesNotExist()
    }

    private fun topAppBarDisplayed() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.add_new_location))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(getString(R.string.refresh))
            .assertIsDisplayed()
    }

    private fun loadingMessageDisplayed(yes: Boolean) {

        fun SemanticsNodeInteraction.toAssert(yes: Boolean) =
            if (yes) SemanticsNodeInteraction::assertIsDisplayed
            else SemanticsNodeInteraction::assertDoesNotExist

        message.toAssert(yes)
        errorMessage.toAssert(yes)
        messageLoading.toAssert(yes)
        progressIndicator.toAssert(yes)
    }

    private fun ComposeContentTestRule.startHome(state: State) = setContent {
        Screen({}, {}, {}, state)
    }
}