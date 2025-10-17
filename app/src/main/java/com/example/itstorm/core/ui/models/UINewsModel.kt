package com.example.itstorm.core.ui.models

import android.net.Uri
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.domain.models.newsmodel.NewsType

data class UINews(
    val id: Long = 0,
    val title: String,
    val content: String,
    val previewImageUri: Uri?,
    val type: String,
    val category: String,
    val timeToRead: String,
    val timeSinceCreation: String,
    val isRead: Boolean,
    val isFavorite: Boolean
)