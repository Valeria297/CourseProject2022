package com.example.hw_3.domain.model

data class PersonDetails(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val repository: Int,
    val experience: Int
)