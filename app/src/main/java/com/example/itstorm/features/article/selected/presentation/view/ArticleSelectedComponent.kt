package com.example.itstorm.features.article.selected.presentation.view

import kotlinx.coroutines.flow.StateFlow
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.State

interface ArticleSelectedComponent {
    val model: StateFlow<State>

    fun onToggleFavorite()

    fun onToggleRead()

    fun onClickClose()
}