package com.example.itstorm.features.news.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.news.presentation.store.NewsStore.Intent
import com.example.itstorm.features.news.presentation.store.NewsStore.State
import com.example.itstorm.features.news.presentation.store.NewsStore.Label

interface NewsStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data class ToggleNewsRead(val id: Long): Intent
        data class ToggleNewsFavorite(val id: Long): Intent
        data class SelectNewsArticle(val id: Long): Intent
        data object ClickWeather: Intent
        data object ClickFavorites: Intent
    }

    data class State(
        val news: List<UINews> = emptyList(),
        val focusedArticle: Long? = null
    )

    sealed interface Label {
        data class SelectNewsArticle(val id: Long): Label
        data object ClickWeather: Label
        data object ClickFavorites: Label
    }
}