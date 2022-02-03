package com.example.hw_3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.hw_3.databinding.FragmentDetailsBinding
import com.example.hw_3.lce.Lce
import com.example.hw_3.model.DetailsViewModel
import com.example.hw_3.provider.ServiceProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentDetails : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: String? by lazy {
        requireArguments().getString("name")
    }

    private val viewModel: DetailsViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel (ServiceProvider.provideGitApi(),
                    requireNotNull(args)) as T
            }
        }
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

        binding.toolbar.setupWithNavController(findNavController())

        viewModel
            .detailsFlow
            .onEach { lce ->
                with(binding) {

                   when(lce) {
                       is Lce.Content -> {
                           binding.progress.isVisible = false

                           val personDetails = lce.value
                           avatar.load(personDetails.avatarUrl)
                           login.text = personDetails.login
                           followers.text = "Followers: ${personDetails.followers}"
                           following.text = "Following: ${personDetails.following}"
                           repository.text = "Repository: ${personDetails.repository}"
                           experience.text = "Experience: ${personDetails.experience}"
                       }
                       is Lce.Loading -> binding.progress.isVisible = true
                       is Lce.Error -> {
                           binding.progress.isVisible = false
                           showErrors("Something went wrong...")
                       }
                   }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


