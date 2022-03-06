package com.example.hw_3.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_3.databinding.ItemUserBinding
import com.example.hw_3.presentation.model.Paging
import com.example.hw_3.data.model.PersonGitHub
import com.example.hw_3.databinding.ItemLoadingBinding
import androidx.recyclerview.widget.ListAdapter as ListAdapter1

class PersonAdapter(
    private val onUserClicked: (PersonGitHub) -> Unit,
) : ListAdapter1<Paging<PersonGitHub>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

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
            else ->  error("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = (getItem(position) as? Paging.Content)?.data ?: return

        (holder as? PersonViewHolder)?.bind(person)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Paging.Content -> TYPE_PERSON
            Paging.Loading -> TYPE_LOADING
            else -> error("something went wrong")
        }
    }

    companion object {
        private const val TYPE_PERSON = 1
        private const val TYPE_LOADING = 2

        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<Paging<PersonGitHub>>() {
            override fun areItemsTheSame(
                item1: Paging<PersonGitHub>,
                item2: Paging<PersonGitHub>,
            ): Boolean {
                return item2 == item1
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                item1: Paging<PersonGitHub>,
                item2: Paging<PersonGitHub>,
            ): Boolean {
                val person = item2 as? Paging.Content
                val newPerson = item1 as? Paging.Content
                return person?.data == newPerson?.data
            }
        }
    }

}