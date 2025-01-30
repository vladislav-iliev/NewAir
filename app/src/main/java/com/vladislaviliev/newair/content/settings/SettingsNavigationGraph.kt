package com.vladislaviliev.newair.content.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.newair.content.settings.aboutDialog.addAboutDialogDestination
import com.vladislaviliev.newair.content.settings.aboutDialog.navigateToAboutDialog
import com.vladislaviliev.newair.content.settings.aboutDialog.onAboutDialogDismissRequest
import com.vladislaviliev.newair.content.settings.deleteAllDialog.addDeleteAllDialogDestination
import com.vladislaviliev.newair.content.settings.deleteAllDialog.navigateToDeleteAllDialog
import com.vladislaviliev.newair.content.settings.deleteAllDialog.onDeleteAllDialogDismissRequest
import com.vladislaviliev.newair.content.settings.deleteFromListDialog.addDeleteFromListDestination
import com.vladislaviliev.newair.content.settings.deleteFromListDialog.navigateToDeleteFromList
import com.vladislaviliev.newair.content.settings.deleteFromListDialog.onDeleteFromListDismissRequest
import com.vladislaviliev.newair.content.settings.mainScreen.SettingsScreenRoute
import com.vladislaviliev.newair.content.settings.mainScreen.addSettingsScreenDestination
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