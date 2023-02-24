package com.example.newair.fragments.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.newair.R
import com.example.newair.data.SensorViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: SensorViewModel by activityViewModels()

    private lateinit var deleteUserLocationsBtn: Preference
    private lateinit var deleteUserLocationBtn: Preference
    private lateinit var aboutDialogBtn: Preference

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.settings_fragment)

        deleteUserLocationsBtn = findPreference(getString(R.string.delete_user_locations_button_key))!!
        deleteUserLocationBtn = findPreference(getString(R.string.delete_user_location_button_key))!!
        aboutDialogBtn = findPreference(getString(R.string.about_dialog_key))!!

        deleteUserLocationsBtn.setOnPreferenceClickListener {
            viewModel.removeAllUserLocations()
            checkDeleteButtons()
            true
        }
        deleteUserLocationBtn.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this)
                .navigate(SettingsFragmentDirections.actionNavigationSettingsToRemoveLocationDialog())
            true
        }
        aboutDialogBtn.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this)
                .navigate(SettingsFragmentDirections.actionNavigationSettingsToAboutDialog())
            true
        }
    }

    override fun onResume() {
        super.onResume()
        checkDeleteButtons()
    }

    private fun checkDeleteButtons() {
        if (viewModel.uiState.value.userLocations.isEmpty()) {
            deleteUserLocationsBtn.isEnabled = false
            deleteUserLocationBtn.isEnabled = false
            return
        }
        deleteUserLocationsBtn.isEnabled = true
        deleteUserLocationBtn.isEnabled = true
    }
}