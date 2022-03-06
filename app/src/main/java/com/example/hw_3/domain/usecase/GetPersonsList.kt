package com.example.hw_3.domain.usecase

import com.example.hw_3.data.model.PersonGitHub
import com.example.hw_3.domain.model.Person
import com.example.hw_3.domain.repository.PersonRepository

class GetPersonsList(private val personRepository: PersonRepository) {

    suspend operator fun invoke(since: Int, end: Int): Result<List<Person>> {
        return personRepository.getPersonsList(since, end)
    }
}