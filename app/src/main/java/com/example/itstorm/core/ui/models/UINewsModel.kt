package com.example.itstorm.core.ui.models

import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import kotlinx.serialization.Serializable

@Serializable
data class UINews(
    val id: Long = 0,
    val title: String,
    val content: String,
    val previewImagePath: String?,
    val type: String,
    val category: NewsCategory,
    val timeToRead: String,
    val timeSinceCreation: String,
    val isRead: Boolean,
    val isFavorite: Boolean
)