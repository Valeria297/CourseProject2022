package com.example.hw_3.data.repository

import com.example.hw_3.data.api.GitAPI
import com.example.hw_3.data.mapper.toLocalModel
import com.example.hw_3.domain.model.Person
import com.example.hw_3.domain.model.PersonDetails
import com.example.hw_3.domain.repository.PersonRepository

class RepositoryImpl(private val gitApi: GitAPI) : PersonRepository {

    override suspend fun getPersonsList(since: Int, end: Int):
            Result<List<Person>> = runCatching {
        gitApi.getUsers(since, end)
    }.map { users -> users.map { it.toLocalModel() } }

    override suspend fun getPersonDetails(login: String):
            Result<PersonDetails> = runCatching {
        gitApi.getUserDetails(login)
    }.map { it.toLocalModel() }
}