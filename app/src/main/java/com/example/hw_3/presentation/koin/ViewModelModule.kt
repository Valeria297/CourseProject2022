package com.example.hw_3.presentation.koin

import com.example.hw_3.presentation.viewmodels.DetailsViewModel
import com.example.hw_3.presentation.viewmodels.PersonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PersonViewModel(get()) }

    viewModel { (login: String) -> DetailsViewModel(get(), login) }
}