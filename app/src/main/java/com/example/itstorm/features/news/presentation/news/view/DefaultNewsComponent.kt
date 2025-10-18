package com.example.itstorm.features.news.presentation.news.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.news.presentation.news.store.NewsStore
import com.example.itstorm.features.news.presentation.news.store.NewsStoreFactory
import kotlinx.coroutines.flow.StateFlow

class DefaultNewsComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    componentContext: ComponentContext
): NewsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        NewsStoreFactory(storeFactory, repository).create()
    }

    override val model: StateFlow<NewsStore.State> = store.stateFlow

    override fun onToggleFavorite(newsId: Long) {
        TODO("Not yet implemented")
    }

    override fun onToggleRead(newsId: Long) {
        TODO("Not yet implemented")
    }

    override fun onArticleSelect(newsId: Long) {
        TODO("Not yet implemented")
    }

    override fun onClickWeather() {
        TODO("Not yet implemented")
    }

    override fun onClickFavorites() {
        TODO("Not yet implemented")
    }


}