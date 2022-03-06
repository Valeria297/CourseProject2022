package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hw_3.databinding.FragmentLanguageBinding
import com.example.hw_3.domain.model.Language
import com.example.hw_3.domain.sharedprefs.SharedPreferences
import org.koin.android.ext.android.inject

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sharedPrefs:SharedPreferences by inject()

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

        with (binding) {

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

                activity?.recreate()
            }

            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}