package com.example.itstorm.features.article.selected.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.core.ui.mappers.toUI
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Intent
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.Label
import com.example.itstorm.features.article.selected.presentation.store.ArticleSelectedStore.State
import kotlinx.coroutines.launch

class ArticleSelectedStoreFactory(
    private val storeFactory: StoreFactory,
    private val newsRepo: NewsRepository,
    private val article: UINews
) {

    fun create(): ArticleSelectedStore =
        object: ArticleSelectedStore, Store<Intent, State, Label> by storeFactory.create(
            name = "NewsStore",
            initialState = State(article = article),
            executorFactory = { NewsExecutor(newsRepo) },
            bootstrapper = SimpleBootstrapper(Action.ArticleLoaded),
            reducer = ArticleSelectedReducer
        ) {}

    private sealed interface Msg {
        data class ArticleLoaded(val article: UINews): Msg
    }

    private sealed interface Action{
        data object ArticleLoaded: Action
    }

    private class NewsExecutor(
        private val newsRepo: NewsRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) =
            when (intent) {
                is Intent.ToggleRead ->
                    changeReadStatus()

                is Intent.ToggleFavorite ->
                    changeFavoriteStatus()

                is Intent.ClickClose ->
                    publish(Label.ClickClose)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.ArticleLoaded -> displayArticle()
            }

        private fun changeReadStatus() {
            val article = state().article
            scope.launch {
                newsRepo.updateReadStatus(article.id, !article.isRead)
            }
        }

        private fun changeFavoriteStatus() {
            val article = state().article
            scope.launch {
                newsRepo.updateFavoriteStatus(article.id, !article.isFavorite)
            }
        }

        private fun displayArticle() {
            val articleId = state().article.id
            scope.launch {
                newsRepo.getNewsFlowById(articleId).collect { article ->
                    dispatch(
                        Msg.ArticleLoaded(
                            article = article.toUI()
                        )
                    )
                }
            }
        }
    }

    private object ArticleSelectedReducer: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.ArticleLoaded ->
                    copy(article = msg.article)
            }
    }
}