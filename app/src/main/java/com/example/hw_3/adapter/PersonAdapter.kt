package com.example.hw_3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_3.databinding.ItemLoadingBinding
import com.example.hw_3.databinding.ItemUserBinding
import com.example.hw_3.paging.PagingData
import com.example.hw_3.model.PersonGitHub
import androidx.recyclerview.widget.ListAdapter as ListAdapter1

class PersonAdapter(
    private val onUserClicked: (PersonGitHub) -> Unit,
) : ListAdapter1<PagingData<PersonGitHub>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return PersonViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false),
            onUserClicked = onUserClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = (getItem(position) as? PagingData.Content)?.data ?: return

        (holder as? PersonViewHolder)?.bind(person)
    }

    companion object {
        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<PagingData<PersonGitHub>>() {
            override fun areItemsTheSame(
                item1: PagingData<PersonGitHub>,
                item2: PagingData<PersonGitHub>,
            ): Boolean {
                return item2 == item1
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                item1: PagingData<PersonGitHub>,
                item2: PagingData<PersonGitHub>,
            ): Boolean {
                val person = item2 as? PagingData.Content
                val newPerson = item1 as? PagingData.Content
                return person?.data == newPerson?.data
            }
        }
    }

}