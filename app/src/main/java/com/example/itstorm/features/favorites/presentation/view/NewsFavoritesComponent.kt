package com.example.itstorm.features.favorites.presentation.view

import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.State
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import kotlinx.coroutines.flow.StateFlow

interface NewsFavoritesComponent {
    val model: StateFlow<State>

    fun onToggleFavorite(newsId: Long)

    fun onFilterNews(filter: NewsCategory?)

    fun onClickWeather()

    fun onClickNews()
}