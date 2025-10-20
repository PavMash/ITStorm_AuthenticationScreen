package com.example.itstorm.core.data.mappers

import com.example.itstorm.core.data.db.entities.NewsEntity
import com.example.itstorm.core.domain.models.newsmodel.DomainNews

fun NewsEntity.toDomain(): DomainNews = DomainNews(
    id = id,
    title = title,
    content = content,
    previewImagePath = previewImagePath,
    type = type,
    category = category,
    timeToRead = timeToRead,
    createdAt = createdAt,
    isRead = isRead,
    isFavorite = isFavorite
)

fun DomainNews.toEntity(): NewsEntity = NewsEntity(
    id = id,
    title = title,
    content = content,
    previewImagePath = previewImagePath,
    type = type,
    category = category,
    timeToRead = timeToRead,
    createdAt = createdAt,
    isRead = isRead,
    isFavorite = isFavorite
)