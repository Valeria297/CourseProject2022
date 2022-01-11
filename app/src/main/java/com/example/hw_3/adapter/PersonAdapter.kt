package com.example.hw_3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_3.paging.PagingData
import com.example.hw_3.person.PersonGitHub
import com.example.hw_3.databinding.ItemLoadingBinding
import com.example.hw_3.databinding.ItemUserBinding


class PersonAdapter(
    private val onUserClicked: (PersonGitHub) -> Unit
) : ListAdapter<PagingData<PersonGitHub>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_PERSON -> {
                PersonViewHolder(
                    binding = ItemUserBinding.inflate(layoutInflater, parent, false),
                    onUserClicked = onUserClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Unsupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = (getItem(position) as? PagingData.Content)?.data ?: return

        (holder as? PersonViewHolder)?.bind(person)
    }

    companion object {
        private const val TYPE_PERSON = 1
        private const val TYPE_LOADING = 2

        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<PagingData<PersonGitHub>>() {
            override fun areItemsTheSame(
                item1: PagingData<PersonGitHub>,
                item2: PagingData<PersonGitHub>
            ): Boolean {
                return item2 == item1
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                item1: PagingData<PersonGitHub>,
                item2: PagingData<PersonGitHub>
            ): Boolean {
                val person = item2 as? PagingData.Content
                val newPerson = item1 as? PagingData.Content
                return person?.data == newPerson?.data
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingData.Content -> TYPE_PERSON
            PagingData.Loading -> TYPE_LOADING
        }
    }

}