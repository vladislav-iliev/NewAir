package com.vladislaviliev.newair.screens.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.screens.settings.aboutDialog.addAboutDialogDestination
import com.vladislaviliev.newair.screens.settings.aboutDialog.navigateToAboutDialog
import com.vladislaviliev.newair.screens.settings.aboutDialog.onAboutDialogDismissRequest
import com.vladislaviliev.newair.screens.settings.deleteAllDialog.addDeleteAllDialogDestination
import com.vladislaviliev.newair.screens.settings.deleteAllDialog.navigateToDeleteAllDialog
import com.vladislaviliev.newair.screens.settings.deleteAllDialog.onDeleteAllDialogDismissRequest
import com.vladislaviliev.newair.screens.settings.deleteFromListDialog.addDeleteFromListDestination
import com.vladislaviliev.newair.screens.settings.deleteFromListDialog.navigateToDeleteFromList
import com.vladislaviliev.newair.screens.settings.deleteFromListDialog.onDeleteFromListDismissRequest
import com.vladislaviliev.newair.screens.settings.screen.ScreenRoute
import com.vladislaviliev.newair.screens.settings.screen.addSettingsScreenDestination
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraphRoute

fun NavGraphBuilder.addSettingsGraph(controller: NavController) {
    navigation<SettingsGraphRoute>(ScreenRoute, builder = { addDestinations(controller) })
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addSettingsScreenDestination(
        controller::navigateToDeleteFromList,
        controller::navigateToDeleteAllDialog,
        controller::navigateToAboutDialog
    )
    addDeleteFromListDestination(controller::onDeleteFromListDismissRequest)
    addDeleteAllDialogDestination(controller::onDeleteAllDialogDismissRequest)
    addAboutDialogDestination(controller::onAboutDialogDismissRequest)
}