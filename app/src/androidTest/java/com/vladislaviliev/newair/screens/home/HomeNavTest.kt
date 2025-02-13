package com.vladislaviliev.newair.screens.home

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.newair.CommonFunctions.getNavController
import com.vladislaviliev.newair.CommonFunctions.getString
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.navigation.createAppGraph
import com.vladislaviliev.newair.screens.home.locationPicker.navigateToLocationPicker
import com.vladislaviliev.newair.screens.home.screen.state.Loading
import com.vladislaviliev.newair.screens.home.screen.uiComponents.Screen
import com.vladislaviliev.newair.screens.map.addLocation.navigateToAddLocationGraph
import com.vladislaviliev.newair.screens.map.addLocation.screen.AddLocationRoute
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.vladislaviliev.newair.screens.home.locationPicker.Route as LocationPickerRoute

@RunWith(AndroidJUnit4::class)
class HomeNavTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun home_navigates_to_add_location() {

        val navController = getNavController()

        composeTestRule.setContent {
            navController.setGraph(createAppGraph(navController), null)
            Screen(navController::navigateToAddLocationGraph, {}, {}, Loading.value)
        }

        composeTestRule
            .onNodeWithContentDescription(getString(R.string.add_new_location)).performClick()
        assert(navController.currentBackStackEntry?.destination?.hasRoute<AddLocationRoute>() == true)
        composeTestRule.onNodeWithText(getString(R.string.select_location)).isDisplayed()
    }

    @Test
    fun home_navigates_to_location_picker() {

        val navController = getNavController()

        composeTestRule.setContent {
            navController.setGraph(createAppGraph(navController), null)
            Screen(
                {},
                {},
                navController::navigateToLocationPicker,
                Loading.value.copy(location = "Test")
            )
        }

        composeTestRule.onRoot().printToLog("TAG")

        composeTestRule
            .onNodeWithContentDescription(getString(R.string.click_to_change_location), true)
            .performClick()
        assert(navController.currentBackStackEntry?.destination?.hasRoute<LocationPickerRoute>() == true)
        composeTestRule.onNodeWithText(getString(R.string.select_location)).isDisplayed()

    }
}