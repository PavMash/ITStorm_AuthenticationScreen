package com.example.itstorm.root.flow.app

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.itstorm.features.favorites.presentation.view.NewsFavoritesUI
import com.example.itstorm.features.news.presentation.view.NewsUI
import com.example.itstorm.features.weather.presentation.view.WeatherUI
import com.example.itstorm.root.flow.app.AppFlowComponent.Child

@Composable
fun AppFlowUI(appFlowComponent: AppFlowComponent) {
    Children(stack = appFlowComponent.stack,){ child ->
        when(val instance = child.instance){
            is Child.Weather -> WeatherUI(instance.component)
            is Child.News -> NewsUI(instance.component)
            is Child.Favorites -> NewsFavoritesUI(instance.component)
        }
    }
}