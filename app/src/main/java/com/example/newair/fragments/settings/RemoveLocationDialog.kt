package com.example.newair.fragments.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.newair.R
import com.example.newair.data.SensorViewModel

internal class RemoveLocationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val builder = AlertDialog.Builder(requireContext()).setTitle(R.string.delete_user_location_button_title)
        addListeners(builder, setUpDeletionList())
        return builder.create()
    }

    private fun setUpDeletionList(): Array<String> {
        val viewModel: SensorViewModel by activityViewModels()
        return viewModel.uiState.value.userLocations.map { it.name }.toTypedArray()
    }

    private fun addListeners(builder: AlertDialog.Builder, items: Array<String>): AlertDialog.Builder {
        val viewModel: SensorViewModel by activityViewModels()
        builder.setItems(items) { _, pos -> viewModel.removeUserLocation(items[pos]) }
        builder.setNegativeButton(android.R.string.cancel) { d, _ -> d.cancel() }
        return builder
    }
}