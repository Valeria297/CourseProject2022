package com.example.hw_3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hw_3.R
import com.example.hw_3.adapter.PersonAdapter
import com.example.hw_3.databinding.FragmentListBinding
import com.example.hw_3.model.PersonViewModel
import com.example.hw_3.paging.PagingData
import com.example.hw_3.provider.ServiceProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentList : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: PersonViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel(ServiceProvider.provideGitApi()) as T
            }
        }
    }

    private val adapter = PersonAdapter { person ->
        findNavController().navigate(
            R.id.details, bundleOf("name" to person.login)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )

            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager

            viewModel
                .personFlow
                .onEach { persons ->
                    val tempList = adapter.currentList
                        .dropLastWhile { it == PagingData.Loading }
                        .plus(persons.map { PagingData.Content(it) })
                        .plus(PagingData.Loading)
                    adapter.submitList(tempList)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun SwipeRefreshLayout.onRefreshListener() = callbackFlow {
        setOnRefreshListener {
            trySend(Unit)
        }

        awaitClose {
            setOnRefreshListener(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PAGE_SIZE = 100
    }

}



