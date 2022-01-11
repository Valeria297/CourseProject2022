package com.example.hw_3.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.hw_3.person.PersonGitHub
import com.example.hw_3.databinding.ItemUserBinding

class PersonViewHolder(
    private val binding: ItemUserBinding,
    private val onUserClicked: (PersonGitHub) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: PersonGitHub) {
        with(binding) {
            image.load(user.avatarUrl) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            textName.text = user.login

            root.setOnClickListener {
                onUserClicked(user)
            }
        }
    }
}