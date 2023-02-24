package com.example.newair.fragments.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.newair.R

/**
 * Dialog describing the app Authors and other referenced authors
 * @author Vladislav Iliev
 */
internal class AboutDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog = AlertDialog.Builder(requireContext())
        .setTitle(R.string.about_dialog_title)
        .setMessage(R.string.about_dialog_contents)
        .setPositiveButton(android.R.string.ok) { _, _ -> findNavController().popBackStack() }
        .create()
}