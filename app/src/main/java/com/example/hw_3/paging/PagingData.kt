package com.example.hw_3.paging

sealed class PagingData<out T> {
    data class Content<T>(val data: T) : PagingData<T>()

    object Loading : PagingData<Nothing>()
}