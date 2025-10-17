package com.example.itstorm.core.domain.repositories

import com.example.itstorm.core.domain.models.newsmodel.DomainNews
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNews(): Flow<List<DomainNews>>

    fun getFavoriteNews(): Flow<List<DomainNews>>

    suspend fun getNewsById(id: Long): DomainNews?

    suspend fun preloadNews(initialNews: List<DomainNews>)

    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    suspend fun updateReadStatus(id: Long, isRead: Boolean)
}