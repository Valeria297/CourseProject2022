package com.example.hw_3.presentation.paging3

data class PagingData(
    val countMax: Int,
    val countToLoad: Int = 0,
) {

    init {
        require(countToLoad >= 0 || countMax > 0) {
            "CountToLoad or CountMax is doubtful!"
        }
    }

    operator fun inc(): PagingData = copy(countToLoad = countToLoad + countMax)
}