package com.example.newair.fragments.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.newair.R
import com.example.newair.data.SensorViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class RemoveLocationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_user_location_dialog_title)
        addListeners(builder, setUpDeletionList())
        return builder.create()
    }

    private fun setUpDeletionList(): Array<String> {
        val viewModel: SensorViewModel by activityViewModels()
        return viewModel.uiState.value!!.userLocations.map { it.name }.toTypedArray()
    }

    private fun addListeners(builder: AlertDialog.Builder, items: Array<String>) {
        val viewModel: SensorViewModel by activityViewModels()
        builder.setItems(items) { d, pos ->
            viewModel.removeUserLocation(items[pos])
            d.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { d, _ -> d.cancel() }
    }
}