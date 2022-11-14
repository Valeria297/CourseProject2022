package com.example.hw_3.presentation.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Paging3ScrollModel(
    private val layoutManager: LinearLayoutManager,
    private val itemsToLoad: Int,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val itemCount = layoutManager.itemCount
        val lastVisiblePos = layoutManager.findLastVisibleItemPosition()

        if (dy != 0 && itemCount <= (lastVisiblePos + itemsToLoad)) {
            recyclerView.post(onLoadMore)
        }
    }
}