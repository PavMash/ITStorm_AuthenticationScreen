package com.example.itstorm.root.flow.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.itstorm.features.favorites.presentation.view.NewsFavoritesComponent
import com.example.itstorm.features.news.presentation.view.NewsComponent
import com.example.itstorm.features.weather.presentation.view.WeatherComponent
import kotlinx.serialization.Serializable

interface AppFlowComponent {

    val stack: Value<ChildStack<*, Child>>

    @Serializable
    sealed interface AppConfig {
        @Serializable
        data object Weather: AppConfig
        @Serializable
        data object News: AppConfig
        @Serializable
        data object Favorites: AppConfig
    }

    sealed interface Child {
        class Weather(val component: WeatherComponent): Child
        class News(val component: NewsComponent): Child
        class Favorites(val component: NewsFavoritesComponent): Child
    }

}