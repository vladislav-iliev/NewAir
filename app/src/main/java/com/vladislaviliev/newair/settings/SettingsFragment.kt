package com.vladislaviliev.newair.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.Vm

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.settings_fragment)
        findPreference<Preference>(getString(R.string.delete_user_location_button_key))!!.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this).navigate(SettingsFragmentDirections.actionNavigationSettingsToRemoveLocationDialog())
            true
        }
        findPreference<Preference>(getString(R.string.delete_user_locations_button_key))!!.setOnPreferenceClickListener {
            val viewModel: Vm by activityViewModels()
            viewModel.removeAllUserLocations()
            true
        }
        findPreference<Preference>(getString(R.string.about_dialog_key))!!.setOnPreferenceClickListener {
            NavHostFragment.findNavController(this).navigate(SettingsFragmentDirections.actionNavigationSettingsToAboutDialog())
            true
        }
    }
}