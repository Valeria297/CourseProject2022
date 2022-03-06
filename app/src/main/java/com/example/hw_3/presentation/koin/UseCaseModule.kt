package com.example.hw_3.presentation.koin

import com.example.domain.usecase.GetPersonDetails
import com.example.domain.usecase.GetPersonsList
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPersonsList(get()) }

    factory { GetPersonDetails(get()) }
}