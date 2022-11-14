package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hw_3.R
import com.example.hw_3.addToolbarInset
import com.example.hw_3.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            theme.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_nightModeFragment)
            }

            language.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_languageFragment)
            }
        }

        binding.appBar.addToolbarInset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}