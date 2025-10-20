package com.example.itstorm.root.flow.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.favorites.presentation.view.DefaultNewsFavoritesComponent
import com.example.itstorm.features.news.presentation.view.DefaultNewsComponent
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
            component = DefaultWeatherComponent(
                storeFactory = DefaultStoreFactory(),
                componentContext = childContext,
                onNewsClicked = ::onNewsClicked,
                onFavoritesClicked = ::onFavoritesClicked
            )
        )
        is AppConfig.News -> AppFlowComponent.Child.News(
            component = DefaultNewsComponent(
                storeFactory = DefaultStoreFactory(),
                repository,
                componentContext = childContext,
                onWeatherClicked = ::onWeatherClicked,
                onFavoritesClicked = ::onFavoritesClicked
            )
        )
        is AppConfig.Favorites -> AppFlowComponent.Child.Favorites(
            component = DefaultNewsFavoritesComponent(
                storeFactory = DefaultStoreFactory(),
                repository,
                componentContext = childContext,
                onWeatherClicked = ::onWeatherClicked,
                onNewsClicked = ::onNewsClicked
            )
        )

    }

    private fun onNewsClicked() {
        navigation.pushToFront(AppConfig.News)
    }

    private fun onFavoritesClicked() {
        navigation.pushToFront(AppConfig.Favorites)
    }

    private fun onWeatherClicked() {
        navigation.pushToFront(AppConfig.Weather)
    }
}