package com.vladislaviliev.newair.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.settings.aboutDialog.addAboutDialogDestination
import com.vladislaviliev.newair.settings.aboutDialog.navigateToAboutDialog
import com.vladislaviliev.newair.settings.aboutDialog.onAboutDialogDismissRequest
import com.vladislaviliev.newair.settings.deleteAllDialog.addDeleteAllDialogDestination
import com.vladislaviliev.newair.settings.deleteAllDialog.navigateToDeleteAllDialog
import com.vladislaviliev.newair.settings.deleteAllDialog.onDeleteAllDialogDismissRequest
import com.vladislaviliev.newair.settings.deleteFromListDialog.addDeleteFromListDestination
import com.vladislaviliev.newair.settings.deleteFromListDialog.navigateToDeleteFromList
import com.vladislaviliev.newair.settings.deleteFromListDialog.onDeleteFromListDismissRequest
import com.vladislaviliev.newair.settings.mainScreen.SettingsScreenRoute
import com.vladislaviliev.newair.settings.mainScreen.addSettingsScreenDestination
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraphRoute

fun NavGraphBuilder.addSettingsGraph(controller: NavController) {
    navigation<SettingsGraphRoute>(SettingsScreenRoute, builder = { addDestinations(controller) })
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

fun NavController.navigateToSettingsGraph() {
    navigate(SettingsGraphRoute)
}