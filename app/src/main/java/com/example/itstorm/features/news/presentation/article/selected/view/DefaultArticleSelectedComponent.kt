package com.example.itstorm.features.news.presentation.article.selected.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.itstorm.core.domain.repositories.NewsRepository

class DefaultArticleSelectedComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    componentContext: ComponentContext
): ArticleSelectedComponent, ComponentContext by componentContext {}