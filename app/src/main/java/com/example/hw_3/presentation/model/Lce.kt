package com.example.hw_3.presentation.model

sealed class Lce<out T> {
    object Loading : Lce<Nothing>()

    data class Content<T>(val value: T) : Lce<T>()

    data class Error(val throwable: Throwable) : Lce<Nothing>()
}