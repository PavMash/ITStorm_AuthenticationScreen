package com.example.itstorm.features.news.presentation.view

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.article.selected.presentation.view.ArticleSelectedComponent
import com.example.itstorm.features.news.presentation.store.NewsStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface NewsComponent {
    val model: StateFlow<NewsStore.State>

    val slot: Value<ChildSlot<*, Child>>

    fun onToggleFavorite(newsId: Long)

    fun onToggleRead(newsId: Long)

    fun onArticleSelect(newsId: Long)

    fun onClickWeather()

    fun onClickFavorites()

    @Serializable
    sealed interface NewsConfig {
        @Serializable
        data class ArticleSelected(val article: UINews): NewsConfig
    }

    sealed interface Child {
        class ArticleSelected(val component: ArticleSelectedComponent): Child
    }
}