package com.example.newair.fragments.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.newair.R
import com.example.newair.data.SensorViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.settings_fragment)
        val deleteUserLocationBtn = findPreference<Preference>(getString(R.string.delete_user_location_button_key))!!
        val deleteUserLocationsBtn = findPreference<Preference>(getString(R.string.delete_user_locations_button_key))!!
        val aboutDialogBtn = findPreference<Preference>(getString(R.string.about_dialog_key))!!

        deleteUserLocationBtn.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this)
                .navigate(SettingsFragmentDirections.actionNavigationSettingsToRemoveLocationDialog())
            true
        }
        deleteUserLocationsBtn.setOnPreferenceClickListener {
            val viewModel: SensorViewModel by activityViewModels()
            viewModel.removeAllUserLocations()
            true
        }
        aboutDialogBtn.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this)
                .navigate(SettingsFragmentDirections.actionNavigationSettingsToAboutDialog())
            true
        }
    }
}