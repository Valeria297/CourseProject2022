package com.example.hw_3.presentation.paging3

import com.example.hw_3.isErrorState
import com.example.hw_3.presentation.model.Paging3LoadType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PagingDataSource<T>(var scope: CoroutineScope,
    var countItem: Int,
    private val data: suspend (PagingData) -> Result<List<T>>
) {

    private val dataFlow = MutableStateFlow<PagingDataLce<T>>(PagingDataLce.StateLoading)
    private val loadFlow = MutableSharedFlow<Paging3LoadType>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var isLoading = false
    private var moreDataPresent = true

    private val firstPage = PagingData(countItem, 0)
    private var lastPage: PagingData? = null

    private val latestData: List<T>? get() = dataFlow.value.dataList

    init {
        scope.launch {
            loadFlow.collectLatest { loadType ->

                if (dataFlow.value.isErrorState()
                    && loadType == Paging3LoadType.LOAD_MORE_DATA) {
                    return@collectLatest
                }

                isLoading = true

                setState(loadType)

                val pageToLoad = loadNextPage(loadType)

                data(pageToLoad)
                    .onSuccess { newData ->
                        moreDataPresent = newData.size == pageToLoad.countMax
                        lastPage = pageToLoad
                        val newContent = PagingDataLce.Content(
                            latestData.orEmpty() + newData,
                            moreDataPresent
                        )
                        dataFlow.tryEmit(newContent)
                    }
                    .onFailure { throwable ->
                        latestData?.let {
                            dataFlow.tryEmit(
                                PagingDataLce.ContentWithError(it, throwable)
                            )
                        } ?: run {
                            dataFlow.tryEmit(
                                PagingDataLce.StateLoadingError(throwable)
                            )
                        }
                    }

                isLoading = false
            }
        }

        getCurrentData()
    }

    private fun loadNextPage(loadType: Paging3LoadType): PagingData {
        return when (loadType) {
            Paging3LoadType.REFRESH_DATA -> firstPage
            Paging3LoadType.LOAD_MORE_DATA,
            Paging3LoadType.GET_CURRENT_DATA -> lastPage?.inc() ?: firstPage
        }
    }

    private fun setState(loadType: Paging3LoadType) {
        when (loadType) {
            Paging3LoadType.REFRESH_DATA -> {
                lastPage = null
                moreDataPresent = true
                dataFlow.tryEmit(
                    PagingDataLce.StateLoading
                )
            }
            Paging3LoadType.GET_CURRENT_DATA -> {
                stateIfNeedCurrentData()
            }
            Paging3LoadType.LOAD_MORE_DATA -> {}
        }
    }

    private fun stateIfNeedCurrentData(){
        when (val value = dataFlow.value) {
            is PagingDataLce.ContentWithError -> {
                dataFlow.tryEmit(
                    PagingDataLce.Content(value.dataList, moreDataPresent)
                )
            }
            is PagingDataLce.StateLoadingError -> {
                dataFlow.tryEmit(
                    PagingDataLce.StateLoading
                )
            }
            else -> "Unknown Lce-state"
        }
    }

    fun subscribePagingData(): Flow<PagingDataLce<T>> {
        return dataFlow.asStateFlow()
    }

    fun loadMore() {
        if (!isLoading && moreDataPresent) {
            loadFlow.tryEmit(Paging3LoadType.LOAD_MORE_DATA)
        }
    }

    fun getCurrentData() {
        loadFlow.tryEmit(Paging3LoadType.GET_CURRENT_DATA)
    }

    fun refreshData() {
        loadFlow.tryEmit(Paging3LoadType.REFRESH_DATA)
    }
}