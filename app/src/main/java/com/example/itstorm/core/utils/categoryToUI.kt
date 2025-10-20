package com.example.itstorm.core.utils

import com.example.itstorm.core.domain.models.newsmodel.NewsCategory

fun categoryToUI(category: NewsCategory?) =
    when(category) {
        (NewsCategory.Technology) -> "Технологии"
        (NewsCategory.Culture) -> "Культура"
        (NewsCategory.Travel) -> "Путешествия"
        else -> "Всё"
    }