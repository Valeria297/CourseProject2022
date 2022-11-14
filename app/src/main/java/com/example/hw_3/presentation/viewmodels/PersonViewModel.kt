package com.example.hw_3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Person
import com.example.domain.usecase.GetPersonsList
import com.example.hw_3.presentation.paging3.PagingDataLce
import com.example.hw_3.presentation.paging3.PagingDataSource
import kotlinx.coroutines.flow.*

class PersonViewModel(private val getPersonsList: GetPersonsList) : ViewModel() {

    private val pagingDataSource = PagingDataSource(viewModelScope, PAGE_SIZE) {
        getPersonsList(it.countToLoad, it.countMax)
    }

    val personPagingData: Flow<PagingDataLce<Person>> = pagingDataSource.subscribePagingData()

    fun onLoadMore() {
        pagingDataSource.loadMore()
    }

    fun onRefresh() {
        pagingDataSource.refreshData()
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}