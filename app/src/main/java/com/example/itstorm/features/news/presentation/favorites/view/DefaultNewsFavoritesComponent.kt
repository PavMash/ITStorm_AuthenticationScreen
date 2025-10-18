package com.example.itstorm.features.news.presentation.favorites.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.itstorm.core.domain.repositories.NewsRepository
import com.example.itstorm.features.news.presentation.favorites.view.NewsFavoritesComponent

class DefaultNewsFavoritesComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    componentContext: ComponentContext
): NewsFavoritesComponent, ComponentContext by componentContext {}