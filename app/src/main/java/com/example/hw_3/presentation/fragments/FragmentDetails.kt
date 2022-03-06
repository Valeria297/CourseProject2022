package com.example.hw_3.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.hw_3.databinding.FragmentDetailsBinding
import com.example.hw_3.presentation.model.Lce
import com.example.hw_3.presentation.viewmodels.DetailsViewModel
import com.example.hw_3.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FragmentDetails : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: String? by lazy {
        requireArguments().getString("name")
    }

    private val viewModel by viewModel<DetailsViewModel> {
        parametersOf(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .detailsFlow
            .onEach { lce ->
                with(binding) {

                    progress.isVisible = lce == Lce.Loading
                    when (lce) {
                        is Lce.Content -> {
                            val personDetails = lce.value
                            avatar.load(personDetails.avatarUrl)
                            login.text = personDetails.login

                            followers.text = "Followers: ${personDetails.followers}"
                            following.text = "Following: ${personDetails.following}"
                            repository.text = "Repository: ${personDetails.repository}"
                            experience.text = "Experience: ${personDetails.experience}"
                        }
                        is Lce.Error -> {
                            requireContext().toast("Something went wrong..")
                        }
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.toolbar.setupWithNavController(findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


