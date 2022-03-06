package com.example.hw_3.presentation.koin

import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PersonRepository> { RepositoryImpl(get()) }
}