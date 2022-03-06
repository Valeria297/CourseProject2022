package com.example.hw_3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_3.presentation.model.Lce
import com.example.hw_3.data.api.GitAPI
import com.example.hw_3.data.model.PersonDetailsGit
import com.example.hw_3.domain.usecase.GetPersonDetails
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val getPersonDetails: GetPersonDetails,
    private val login: String
) : ViewModel() {

    val detailsFlow: Flow<Lce<PersonDetailsGit>> =
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