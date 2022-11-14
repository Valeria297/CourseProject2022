package com.example.hw_3.presentation.paging3

sealed class PagingDataLce<out T> {

    open val dataList: List<T>? get() = null

    object StateLoading : PagingDataLce<Nothing>()

    data class StateLoadingError(
        val t: Throwable
    ) : PagingDataLce<Nothing>()

    data class Content<T>(
        override val dataList: List<T>,
        val hasData: Boolean
    ) : PagingDataLce<T>()

    data class ContentWithError<T>(
        override val dataList: List<T>,
        val t: Throwable
    ) : PagingDataLce<T>()
}