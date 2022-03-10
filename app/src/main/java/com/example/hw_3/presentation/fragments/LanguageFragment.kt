package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.data.model.Language
import com.example.data.sharedprefs.SharedPreferences
import com.example.hw_3.addToolbarInset
import com.example.hw_3.databinding.FragmentLanguageBinding

import org.koin.android.ext.android.inject

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sharedPrefs: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentLanguageBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            when (sharedPrefs.language) {
                Language.EN -> english
                Language.RU -> russian
            }.isChecked = true

            radioGroup.setOnCheckedChangeListener { _, buttonId ->
                val language = when (buttonId) {
                    english.id -> Language.EN
                    russian.id -> Language.RU
                    else -> error("Incorrect button-Id $buttonId")
                }
                sharedPrefs.language = language

                requireActivity().recreate()
            }

            binding.appBar.addToolbarInset()
            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}