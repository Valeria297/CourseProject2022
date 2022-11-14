package com.example.hw_3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_3.presentation.model.Lce
import com.example.domain.model.PersonDetails
import com.example.domain.usecase.GetPersonDetails
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val getPersonDetails: GetPersonDetails,
    private val login: String
) : ViewModel() {

    val detailsFlow: Flow<Lce<PersonDetails>> =
        flow {
            val lceState = getPersonDetails(login)
                .fold(
                    onSuccess = {
                        Lce.Content(it)
                    },
                    onFailure = {
                        Lce.Error(it)
                    }
                )
            emit(lceState)
        }.onStart {
            emit(Lce.Loading)
        }.shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)

}