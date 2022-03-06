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

fun Person.toGitModel(): PersonGitHub {
    return PersonGitHub(
        id = id,
        login = login,
        avatarUrl = avatarUrl
    )
}

fun PersonDetails.toGitModel(): PersonDetailsGit {
    return PersonDetailsGit(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        repository = repository,
        experience = experience
    )
}