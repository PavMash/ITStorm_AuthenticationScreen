package com.example.itstorm.core.domain.models.newsmodel

import android.net.Uri
import java.time.Instant

data class DomainNews (
    val id: Long = 0,
    val title: String,
    val content: String,
    val previewImageUri: Uri?,
    val type: NewsType,
    val category: NewsCategory,
    val timeToRead: Int,
    val createdAt: Instant,
    val isRead: Boolean,
    val isFavorite: Boolean
)