package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.data.model.NightMode
import com.example.data.sharedprefs.SharedPreferences
import com.example.hw_3.R
import com.example.hw_3.addToolbarInset
import com.example.hw_3.databinding.FragmentNightModeBinding
import com.example.hw_3.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

class NightModeFragment : Fragment() {

    private var _binding: FragmentNightModeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sharedPrefs: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentNightModeBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            appBar.addToolbarInset()
            toolbar.setupWithNavController(findNavController())

            when (sharedPrefs.nightMode) {
                NightMode.DARK -> themeDark
                NightMode.LIGHT -> themeLight
                NightMode.SYSTEM -> themeSystem
                NightMode.ENERGY_SAVING -> themeBattery
            }.isChecked = true

            radioGroup.setOnCheckedChangeListener { _, buttonId ->
                val (prefsMode, systemMode) = when (buttonId) {
                    themeDark.id -> NightMode.DARK to AppCompatDelegate.MODE_NIGHT_YES
                    themeLight.id -> NightMode.LIGHT to AppCompatDelegate.MODE_NIGHT_NO
                    themeSystem.id -> NightMode.SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    themeBattery.id -> NightMode.ENERGY_SAVING to AppCompatDelegate.MODE_NIGHT_YES
                    else -> error("Incorrect choice of theme")
                }

                sharedPrefs.nightMode = prefsMode
                AppCompatDelegate.setDefaultNightMode(systemMode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}