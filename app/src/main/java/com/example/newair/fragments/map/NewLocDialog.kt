package com.example.newair.fragments.map

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newair.R
import com.example.newair.data.SensorViewModel
import com.example.newair.data.user_locations.UserLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText

internal class NewLocDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog = AlertDialog.Builder(requireContext())
        .setView(R.layout.dialog_add_custom_location)
        .setTitle(R.string.add_custom_location_dialog_title)
        .setMessage(R.string.add_custom_location_dialog_title)
        .setPositiveButton(android.R.string.ok) { _, _ ->  onPos()}
        .setNegativeButton(android.R.string.cancel) { _, _ ->  }
        .create()

    private fun onPos() {
        val dialog = requireDialog()
        val context = dialog.context
        val input = (dialog.findViewById(R.id.input) as TextInputEditText).text?.toString() ?: ""
        if (input.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.location_empty), Toast.LENGTH_LONG).show()
            return
        }

        val viewModel: SensorViewModel by activityViewModels()
        if (viewModel.userLocationExists(input)) {
            val msg = context.getString(
                R.string.location_already_exists_format, getString(R.string.location_already_exists_toast), input
            )
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            return
        }
        val args: NewLocDialogArgs by navArgs()
        val lat = args.lat.toDouble()
        val lon = args.lon.toDouble()
        viewModel.addUserLocation(UserLocation(input, LatLng(lat, lon)))
        dialog.dismiss()
        findNavController().navigate(NewLocDialogDirections.actionNewLocDialogToNavigationHome())
    }
}