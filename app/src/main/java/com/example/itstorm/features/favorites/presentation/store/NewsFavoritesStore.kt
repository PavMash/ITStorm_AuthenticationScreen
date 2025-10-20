package com.example.itstorm.features.favorites.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Intent
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Label
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.State

interface NewsFavoritesStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data class ClickFilter(val filter: NewsCategory?): Intent
        data object ClickWeather: Intent
        data object ClickNews: Intent
        data class ToggleNewsFavorite(val id: Long): Intent
    }

    data class State(
        val favorites: List<UINews> = emptyList(),
        val appliedFilter: NewsCategory? = null,
        val filtered: List<UINews> = emptyList(),
        val toRemove: List<Long> = emptyList()
    )

    sealed interface Label {
        data object ClickWeather: Label
        data object ClickNews: Label
    }
}