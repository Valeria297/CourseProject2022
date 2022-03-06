package com.example.domain.repository

import com.example.domain.model.Person
import com.example.domain.model.PersonDetails

interface PersonRepository {

    suspend fun getPersonsList(since: Int, end: Int): Result<List<Person>>

    suspend fun getPersonDetails(login: String): Result<PersonDetails>
}