package com.example.itstorm.features.favorites.presentation.store

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.core.ui.mappers.toUI
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Intent
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.State
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore.Label
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFavoritesStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository
) {

    fun create(): NewsFavoritesStore =
        object: NewsFavoritesStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "NewsFavoritesStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Action.NewsLoaded),
                executorFactory = { NewsFavoritesExecutor(repository) },
                reducer = NewsFavoritesReducer
            ){}

    private sealed interface Msg {
        data class NewsLoaded(val news: List<UINews>): Msg
        data class FavoritesFiltered(val filter: NewsCategory?): Msg
        data class MarkAsToRemove(val id: Long): Msg
        data class UnmarkAsToRemove(val id: Long): Msg

    }

    private sealed interface Action{
        data object NewsLoaded: Action
    }

    private class NewsFavoritesExecutor(
        private val newsRepo: NewsRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) =
            when (intent) {
                is Intent.ToggleNewsFavorite ->
                    removeFromFavorites(intent.id)

                is Intent.ClickFilter ->
                    filterNews(intent.filter)

                is Intent.ClickWeather ->
                    publish(Label.ClickWeather)

                is Intent.ClickNews ->
                    publish(Label.ClickNews)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.NewsLoaded -> displayNews()
            }

        private fun filterNews(category: NewsCategory?) {
            dispatch(Msg.FavoritesFiltered(category))
        }
        private fun removeFromFavorites(newsId: Long) {
            val state = state()
            val articleToRemove = state.favorites.find {newsId == it.id}
            articleToRemove?.let {
                dispatch(Msg.MarkAsToRemove(articleToRemove.id))
                scope.launch {
                    delay(1000L)
                    dispatch(Msg.UnmarkAsToRemove(articleToRemove.id))
                    newsRepo.updateFavoriteStatus(newsId, !articleToRemove.isFavorite)
                }
            }
        }

        private fun displayNews() {
            scope.launch {
                newsRepo.getFavoriteNews().collect { newsList ->
                    dispatch(Msg.NewsLoaded(newsList.map { it.toUI() }))
                }
            }
        }
    }

    private object NewsFavoritesReducer: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.NewsLoaded ->
                    copy(
                        favorites = msg.news,
                        appliedFilter = appliedFilter,
                        filtered = filterNews(msg.news, appliedFilter),
                        toRemove = toRemove
                    )
                is Msg.MarkAsToRemove ->
                    copy(
                        favorites = favorites,
                        appliedFilter = appliedFilter,
                        filtered = filtered,
                        toRemove = toRemove + msg.id
                    )
                is Msg.UnmarkAsToRemove ->
                    copy(
                        favorites = favorites,
                        appliedFilter = appliedFilter,
                        filtered = filtered,
                        toRemove = if (toRemove.contains(msg.id))
                            toRemove - msg.id
                        else toRemove
                    )
                is Msg.FavoritesFiltered ->
                    copy(
                        favorites = favorites,
                        appliedFilter = msg.filter,
                        filtered = filterNews(favorites, msg.filter),
                        toRemove = toRemove
                    )
            }

        private fun filterNews(news: List<UINews>, filter: NewsCategory?): List<UINews>  =
            if (filter != null)
                news.filter { it.category == filter }
            else
                news
        }
}