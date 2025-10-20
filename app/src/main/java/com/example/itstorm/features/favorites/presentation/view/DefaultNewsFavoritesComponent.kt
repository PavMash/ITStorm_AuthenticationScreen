package com.example.itstorm.features.favorites.presentation.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.State
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Intent
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Label
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNewsFavoritesComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    onWeatherClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    componentContext: ComponentContext
): NewsFavoritesComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        NewsFavoritesStoreFactory(storeFactory, repository).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.ClickWeather -> {
                        onWeatherClicked()
                    }
                    is Label.ClickNews -> {
                        onNewsClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<State> = store.stateFlow

    override fun onToggleFavorite(newsId: Long) {
        store.accept(Intent.ToggleNewsFavorite(newsId))
    }

    override fun onFilterNews(filter: NewsCategory?) {
        store.accept(Intent.ClickFilter(filter))
    }

    override fun onClickWeather() {
        store.accept(Intent.ClickWeather)
    }

    override fun onClickNews() {
        store.accept(Intent.ClickNews)
    }

}