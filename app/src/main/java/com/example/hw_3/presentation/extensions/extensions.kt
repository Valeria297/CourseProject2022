package com.example.hw_3

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.data.model.Language
import com.example.hw_3.presentation.model.Paging3ScrollModel
import com.example.hw_3.presentation.paging3.PagingDataLce
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

//Locale extension
fun Context.applySelectedAppLanguage(language: Language): Context {
    val newConfig = Configuration(resources.configuration)
    Locale.setDefault(language.locale)
    newConfig.setLocale(language.locale)

    return createConfigurationContext(newConfig)
}

//Toast extension
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

//Paging3 extension
fun PagingDataLce<*>.isErrorState(): Boolean {
    return this is PagingDataLce.StateLoadingError
            || this is PagingDataLce.ContentWithError
}

fun RecyclerView.onPaginationScrolling(
    layoutManager: LinearLayoutManager,
    itemsToLoad: Int,
) = callbackFlow {
    val scrollListener = Paging3ScrollModel(layoutManager, itemsToLoad) { trySend(Unit) }

    addOnScrollListener(scrollListener)
    awaitClose { removeOnScrollListener(scrollListener) }
}

//Extension to refresh data
fun SwipeRefreshLayout.onRefresh() = callbackFlow {
    setOnRefreshListener { trySend(Unit) }
    awaitClose { setOnRefreshListener(null) }
}

//Insets extension
fun AppBarLayout.addToolbarInset() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        updatePadding(
            top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        )
        WindowInsetsCompat.Builder(insets)
            .setInsets(WindowInsetsCompat.Type.statusBars(), Insets.NONE)
            .build()
    }
}

