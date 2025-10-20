package com.example.itstorm.features.news.presentation.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.article.selected.presentation.view.DefaultArticleSelectedComponent
import com.example.itstorm.features.news.presentation.store.NewsStore
import com.example.itstorm.features.news.presentation.store.NewsStoreFactory
import com.example.itstorm.features.news.presentation.view.NewsComponent.NewsConfig
import com.example.itstorm.features.news.presentation.view.NewsComponent.Child
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNewsComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    componentContext: ComponentContext,
    onWeatherClicked: () -> Unit,
    onFavoritesClicked: () -> Unit
): NewsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        NewsStoreFactory(storeFactory, repository).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is NewsStore.Label.ClickWeather -> {
                        onWeatherClicked()
                    }
                    is NewsStore.Label.ClickFavorites -> {
                        onFavoritesClicked()
                    }
                    is NewsStore.Label.SelectNewsArticle -> {}
                }
            }
        }
    }

    override val model: StateFlow<NewsStore.State> = store.stateFlow

    val navigation = SlotNavigation<NewsConfig>()
    override val slot: Value<ChildSlot<*, Child>> =
        childSlot(
            source = navigation,
            serializer = NewsConfig.serializer(),
            handleBackButton = true,
            childFactory = ::createChildSlot
        )

    private fun createChildSlot(
        config: NewsConfig,
        childContext: ComponentContext
    ): Child = when (config) {
        is NewsConfig.ArticleSelected ->
            Child.ArticleSelected(
                component = DefaultArticleSelectedComponent(
                    storeFactory = DefaultStoreFactory(),
                    repository = repository,
                    article = config.article,
                    componentContext = childContext,
                    onCloseClicked = ::onSelectedArticleClose
                )
            )
    }

    private fun onSelectedArticleClose() {
        navigation.dismiss()
    }

    override fun onToggleFavorite(newsId: Long) {
        store.accept(NewsStore.Intent.ToggleNewsFavorite(newsId))
    }

    override fun onToggleRead(newsId: Long) {
        store.accept(NewsStore.Intent.ToggleNewsRead(newsId))
    }

    override fun onArticleSelect(newsId: Long) {
        val article = store.state.news.find { it.id == newsId }
        article?.let {
            navigation.activate(NewsConfig.ArticleSelected(article))
        }
    }

    override fun onClickWeather() {
        store.accept(NewsStore.Intent.ClickWeather)
    }

    override fun onClickFavorites() {
        store.accept(NewsStore.Intent.ClickFavorites)
    }


}