package com.example.domain.usecase

import com.example.domain.model.Person
import com.example.domain.repository.PersonRepository

class GetPersonsList(private val personRepository: PersonRepository) {

    suspend operator fun invoke(since: Int, end: Int): Result<List<Person>> {
        return personRepository.getPersonsList(since, end)
    }
}