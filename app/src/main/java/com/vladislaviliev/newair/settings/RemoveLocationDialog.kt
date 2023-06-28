package com.vladislaviliev.newair.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.RuntimeData
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class RemoveLocationDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val data: RuntimeData by activityViewModels()
        val items = data.userLocations.map { it.name }.toTypedArray()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_user_location_dialog_title)
            .setItems(items) { d, pos -> data.removeUserLocation(items[pos]); d.dismiss() }
            .setNegativeButton(android.R.string.cancel) { d, _ -> d.cancel() }
            .create()
    }
}