package com.example.hw_3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_3.paging.PagingData
import com.example.hw_3.person.PersonGitHub
import com.example.hw_3.R
import com.example.hw_3.retrofit.GitService
import com.example.hw_3.adapter.PersonAdapter
import com.example.hw_3.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentList : Fragment() {
    private var _binding: FragmentListBinding? = null

    private val binding get() = requireNotNull(_binding) {
        "View does not exist anymore"
    }

    private val adapter = PersonAdapter { person ->
        findNavController().navigate(
            R.id.details, bundleOf("name" to person.login)
        )
    }

    private var isLoading = false
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPersons()

        with(binding) {

            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )

            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                    if (!isLoading && dy != 0 && totalItemCount
                        <= (lastVisibleItem + COUNT_TO_LOAD)) {
                        recyclerView.post {
                            loadPersons()
                        }
                    }
                }
            })
        }
    }

    private fun loadPersons(onLoadingFinished: () -> Unit = {}) {
        if (isLoading) return
        isLoading = true

        val loadingFinishedCallback = {
            isLoading = false
            onLoadingFinished()
        }

        val since = currentPage * PAGE_SIZE
        val gitService = GitService()
        gitService.getGitApi().getUsers(since, PAGE_SIZE)
            .enqueue(object : Callback<List<PersonGitHub>> {
                override fun onResponse(
                    call: Call<List<PersonGitHub>>,
                    response: Response<List<PersonGitHub>>
                ) {
                    if (response.isSuccessful) {
                        val newList = adapter.currentList
                            .dropLastWhile { it == PagingData.Loading }
                            .plus(response.body()?.map { PagingData.Content(it) }.orEmpty())
                            .plus(PagingData.Loading)
                        adapter.submitList(newList)
                        currentPage++
                    } else {
                        showErrors(response.errorBody()?.string() ?: "Something went wrong..")
                    }

                    loadingFinishedCallback()
                }

                override fun onFailure(call: Call<List<PersonGitHub>>, t: Throwable) {
                    showErrors(t.message ?: "Something went wrong..")
                    loadingFinishedCallback()
                }
            })
    }

    private fun showErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PAGE_SIZE = 30
        private const val COUNT_TO_LOAD = 10

    }
}