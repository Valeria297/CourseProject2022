package com.example.hw_3.presentation.koin

import com.example.hw_3.data.repository.RepositoryImpl
import com.example.hw_3.domain.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PersonRepository> { RepositoryImpl(get()) }
}