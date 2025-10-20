package com.example.itstorm.features.article.selected.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Intent
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.State
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Label

interface ArticleSelectedStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickClose: Intent
        data object ToggleFavorite: Intent
        data object ToggleRead: Intent
    }

    data class State(
        val article: UINews
    )

    sealed interface Label {
        data object ClickClose: Label
    }
}