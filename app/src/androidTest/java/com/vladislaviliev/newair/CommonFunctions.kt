package com.vladislaviliev.newair

import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry

internal object CommonFunctions {

    fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext

    fun getString(id: Int) = getContext().getString(id)

    fun getNavController() = TestNavHostController(getContext()).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}