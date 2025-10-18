package com.example.itstorm.root.flow.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.news.presentation.favorites.view.DefaultNewsFavoritesComponent
import com.example.itstorm.features.news.presentation.news.view.DefaultNewsComponent
import com.example.itstorm.features.weather.presentation.view.DefaultWeatherComponent
import com.example.itstorm.root.flow.app.AppFlowComponent.AppConfig

class DefaultAppFlowComponent(
    componentContext: ComponentContext,
    private val repository: NewsRepository
): AppFlowComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<AppConfig>()

    override val stack = childStack(
        source = navigation,
        serializer = AppConfig.serializer(),
        childFactory = ::createChild,
        handleBackButton = true,
        initialConfiguration = AppConfig.Weather
    )

    private fun createChild(
        config: AppConfig,
        childContext: ComponentContext
    ) = when(config) {
        is AppConfig.Weather -> AppFlowComponent.Child.Weather(
            DefaultWeatherComponent(
                DefaultStoreFactory(),
                childContext,
                ::onNewsClicked,
                ::onFavoritesClicked
            )
        )
        is AppConfig.News -> AppFlowComponent.Child.News(
            DefaultNewsComponent(
                DefaultStoreFactory(),
                repository,
                childContext
            )
        )
        is AppConfig.Favorites -> AppFlowComponent.Child.Favorites(
            DefaultNewsFavoritesComponent(
                DefaultStoreFactory(),
                repository,
                childContext
            )
        )

    }

    private fun onNewsClicked() {
        navigation.pushToFront(AppConfig.News)
    }

    private fun onFavoritesClicked() {
        navigation.pushToFront(AppConfig.Favorites)
    }
}