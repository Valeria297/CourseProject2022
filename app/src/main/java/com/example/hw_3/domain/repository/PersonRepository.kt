package com.example.hw_3.domain.repository

import com.example.hw_3.domain.model.Person
import com.example.hw_3.domain.model.PersonDetails

interface PersonRepository {

    suspend fun getPersonsList(since: Int, end: Int): Result<List<Person>>

    suspend fun getPersonDetails(login: String): Result<PersonDetails>
}