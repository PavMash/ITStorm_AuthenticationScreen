package com.example.itstorm.features.article.selected.presentation.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStoreFactory
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.State
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Intent
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Label
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultArticleSelectedComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    private val article: UINews,
    componentContext: ComponentContext,
    onCloseClicked: () -> Unit
): ArticleSelectedComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ArticleSelectedStoreFactory(storeFactory, repository,
            article).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.ClickClose -> {
                        onCloseClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<State> = store.stateFlow

    override fun onToggleFavorite() {
        store.accept(Intent.ToggleFavorite)
    }

    override fun onToggleRead() {
        store.accept(Intent.ToggleRead)
    }

    override fun onClickClose() {
        store.accept(Intent.ClickClose)
    }
}