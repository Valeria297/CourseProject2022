package com.example.hw_3.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hw_3.R
import com.example.hw_3.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {

    private var _binding: FragmentBasicBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentBasicBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller =
            (childFragmentManager.findFragmentById(R.id.fragment_container)
                    as NavHostFragment)
                .navController
        binding.bottomNavigation.setupWithNavController(controller)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}