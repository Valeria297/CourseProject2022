package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hw_3.R
import com.example.hw_3.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_night_mode.*

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

        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { _, insets ->
            app_bar.updatePadding(
                top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            )
            insets
        }

        toolbar.setupWithNavController(findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}