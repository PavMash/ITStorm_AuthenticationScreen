package com.example.itstorm.features.news.presentation.news.view

import com.example.itstorm.features.news.presentation.news.store.NewsStore
import kotlinx.coroutines.flow.StateFlow

interface NewsComponent {
    val model: StateFlow<NewsStore.State>

    fun onToggleFavorite(newsId: Long)

    fun onToggleRead(newsId: Long)

    fun onArticleSelect(newsId: Long)

    fun onClickWeather()

    fun onClickFavorites()
}