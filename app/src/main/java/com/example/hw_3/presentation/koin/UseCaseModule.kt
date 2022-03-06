package com.example.hw_3.presentation.koin

import com.example.hw_3.domain.usecase.GetPersonDetails
import com.example.hw_3.domain.usecase.GetPersonsList
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPersonsList(get()) }

    factory { GetPersonDetails(get()) }
}