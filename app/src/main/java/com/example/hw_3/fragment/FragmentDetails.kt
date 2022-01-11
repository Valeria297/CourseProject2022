package com.example.hw_3.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.hw_3.databinding.FragmentDetailsBinding
import com.example.hw_3.person.PersonDetailsGit
import com.example.hw_3.retrofit.GitService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentDetails : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding) {
        "View does not exist anymore"
    }

   private val args: String? by lazy{
       requireArguments().getString("name")
   }

   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPersonDetails()

        with(binding) {
            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPersonDetails() {
        val gitService = GitService()

        args?.let {
            gitService.getGitApi().getUserDetails(it)
                .enqueue(object : Callback<PersonDetailsGit> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<PersonDetailsGit>,
                        response: Response<PersonDetailsGit>
                    ) {
                        if (response.isSuccessful) {
                            val personDetails = response.body() ?: return
                            with(binding) {
                                avatar.load(personDetails.avatarUrl)
                                login.text = personDetails.login
                                followers.text = "Followers: ${personDetails.followers}"
                                following.text = "Following: ${personDetails.following}"
                                repository.text = "Repository: ${personDetails.repository}"
                                experience.text = "Experience: ${personDetails.experience}"
                            }
                        } else {
                            showError(response.errorBody()?.string() ?: "")
                        }
                    }

                    override fun onFailure(call: Call<PersonDetailsGit>, t: Throwable) {
                        showError(t.message ?: "Something went wrong..")
                    }
                })
        }
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }

}


