package com.example.domain.usecase

import com.example.domain.model.PersonDetails
import com.example.domain.repository.PersonRepository

class GetPersonDetails(private val personRepository: PersonRepository) {

    suspend operator fun invoke(login: String): Result<PersonDetails> {
        return personRepository.getPersonDetails(login)
    }
}