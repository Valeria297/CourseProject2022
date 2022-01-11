package com.example.hw_3.person

import com.google.gson.annotations.SerializedName

class PersonGitHub(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

