package com.vladislaviliev.newair.fragments.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.Vm
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class RemoveLocationDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val viewModel: Vm by activityViewModels()
        val items = viewModel.userLocations.map { it.name }.toTypedArray()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_user_location_dialog_title)
            .setItems(items) { d, pos -> viewModel.removeUserLocation(items[pos]); d.dismiss() }
            .setNegativeButton(android.R.string.cancel) { d, _ -> d.cancel() }
            .create()
    }
}