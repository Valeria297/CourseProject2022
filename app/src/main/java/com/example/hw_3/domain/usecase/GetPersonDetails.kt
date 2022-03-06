package com.example.hw_3.domain.usecase

import com.example.hw_3.data.model.PersonDetailsGit
import com.example.hw_3.domain.model.PersonDetails
import com.example.hw_3.domain.repository.PersonRepository

class GetPersonDetails(private val personRepository: PersonRepository) {

    suspend operator fun invoke(login: String): Result<PersonDetails> {
        return personRepository.getPersonDetails(login)
    }
}