package com.example.hw_3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_3.lce.Lce
import com.example.hw_3.retrofit.GitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PersonViewModel(private val gitApi: GitAPI) : ViewModel() {

    private val _personFlow = MutableSharedFlow<List<PersonGitHub>>()
    val personFlow: Flow<List<PersonGitHub>> = _personFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: Flow<Throwable> = _errorFlow.asSharedFlow()

    private var currentPage = 0
    private var isLoading = false

    init {
        loadData()
    }

    fun onRefresh() {
        currentPage = 0
        loadData()
    }

    private fun loadData() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val since = currentPage * PAGE_SIZE

            try {
                val persons = gitApi.getUsers(since, PAGE_SIZE)
                _personFlow.emit(persons)
                currentPage++
            } catch (e: Throwable) {
                _errorFlow.emit(e)
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }

    sealed class Lce<out T> {
        object Loading : Lce<Nothing>()

        data class Content<T>(val value: T) : Lce<T>()

        data class Error(val throwable: Throwable) : Lce<Nothing>()
    }

}