package com.example.data.repository

import com.example.data.api.GitAPI
import com.example.data.mapper.toLocalModel
import com.example.domain.model.Person
import com.example.domain.model.PersonDetails
import com.example.domain.repository.PersonRepository

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