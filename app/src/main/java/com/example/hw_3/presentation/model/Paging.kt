package com.example.hw_3.presentation.model

sealed class Paging<out T> {
    data class Content<T>(val data: T) : Paging<T>()

    object Loading : Paging<Nothing>()

    object Error : Paging<Nothing>()
}