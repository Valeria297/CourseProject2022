package com.example.hw_3.data.mapper

import com.example.hw_3.data.model.PersonDetailsGit
import com.example.hw_3.data.model.PersonGitHub
import com.example.hw_3.domain.model.Person
import com.example.hw_3.domain.model.PersonDetails

fun PersonGitHub.toLocalModel(): Person {
    return Person(
        id = id,
        login = login,
        avatarUrl = avatarUrl
    )
}

fun PersonDetailsGit.toLocalModel(): PersonDetails {
    return PersonDetails(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        repository = repository,
        experience = experience
    )
}