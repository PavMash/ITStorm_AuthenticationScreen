package com.example.itstorm.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.domain.models.newsmodel.NewsType
import java.time.Instant

@Entity(tableName = "news")
data class NewsEntity (
    @PrimaryKey val id: Long = 0,
    val title: String,
    val content: String,
    val previewImagePath: String?,
    val type: NewsType,
    val category: NewsCategory,
    val timeToRead: Int,
    val createdAt: Instant,
    val isRead: Boolean,
    val isFavorite: Boolean
)