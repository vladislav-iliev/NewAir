package com.vladislaviliev.newair

import android.content.Context
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider

internal object CommonFunctions {

    fun getContext() = ApplicationProvider.getApplicationContext<Context>()

    fun getString(id: Int) = getContext().getString(id)

    fun getNavController() = TestNavHostController(getContext()).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}