package com.example.hw_3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_3.lce.Lce
import com.example.hw_3.retrofit.GitAPI
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val gitApi: GitAPI,
    private val login: String
) : ViewModel() {

    val detailsFlow: Flow<Lce<PersonDetailsGit>> =
        flow {
            val lceState = runCatching { gitApi.getUserDetails(login) }
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