package com.example.hw_3.domain.usecase

import com.example.hw_3.data.model.PersonDetailsGit
import com.example.hw_3.domain.repository.PersonRepository

class GetPersonDetails(private val personRepository: PersonRepository) {

    suspend operator fun invoke(login: String): Result<PersonDetailsGit> {
        return personRepository.getPersonDetails(login)
    }
}