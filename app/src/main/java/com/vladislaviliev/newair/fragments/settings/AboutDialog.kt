package com.vladislaviliev.newair.fragments.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.vladislaviliev.newair.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class AboutDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.about_dialog_contents)
            .setPositiveButton(android.R.string.ok) { _, _ -> findNavController().popBackStack() }
            .create()
}